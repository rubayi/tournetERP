import { Notify } from "quasar";
import { AxiosResponse } from "axios";

let lastErrorNotification: CallableFunction | null;
let ongoingNotification: CallableFunction | null;
let allNotifications: CallableFunction[] = [];

class NotificationHelper {
  createSuccessNotification(message: string): void {
    const notification = Notify.create({
      actions: [
        {
          icon: "close",
          color: "white",
          handler: () => {
            /* ... */
          },
        },
      ],
      message: message,
      position: "top-right",
      timeout: 1500,
      type: "positive",
    });
    allNotifications.push(notification);
  }
  createErrorNotification(message: string): void {
    lastErrorNotification = Notify.create({
      actions: [
        {
          icon: "close",
          color: "white",
          handler: () => {
            /* ... */
          },
        },
      ],
      message: message,
      position: "top-right",
      timeout: 0,
      type: "negative",
    });
    allNotifications.push(lastErrorNotification);
  }
  // If more than one ongoing notifications exist at the same time the parent component can use the return value
  // to dismiss the notification
  createOngoingNotification(message: string): CallableFunction {
    ongoingNotification = Notify.create({
      actions: [
        {
          icon: "close",
          color: "white",
          handler: () => {
            /* ... */
          },
        },
      ],
      message: message,
      position: "top-right",
      timeout: 0,
      type: "ongoing",
    });
    allNotifications.push(ongoingNotification);
    return ongoingNotification;
  }
  createInfoNotification(message: string): void {
    const notification = Notify.create({
      actions: [
        {
          icon: "close",
          color: "white",
          handler: () => {
            /* ... */
          },
        },
      ],
      message: message,
      position: "top-right",
      timeout: 1000,
      type: "info",
    });
    allNotifications.push(notification);
  }

  formatResponseToErrorMessage(response: AxiosResponse | undefined): string {
    let message;
    if (!response) {
      message =
        "No response retrieved from the HWY Application. The HWY Application may be offline or there may be problems with the internet.";
    } else if (response.data.message && response.data.message.length > 0) {
      message = response.data.message;
    } else if (
      response.data &&
      !response.data.message &&
      response.data.length > 0
    ) {
      message = response.data;
    } else if (response.status) {
      message = `(Status Code ${response.status}) ${response.statusText} - Contact IT Staff for assistance`;
    }
    return message;
  }

  dismiss(): void {
    allNotifications.forEach((notification) => {
      notification();
    });
    allNotifications = [];
  }

  dismissLastErrorNotification(): void {
    if (lastErrorNotification) {
      lastErrorNotification();
    }
    lastErrorNotification = null;
  }

  dismissOngoingNotification(): void {
    if (ongoingNotification) {
      ongoingNotification();
    }
    ongoingNotification = null;
  }
}

export const notificationHelper = new NotificationHelper();
