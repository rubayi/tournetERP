import api from "./api";
import authHeader from "src/services/auth-header";
import { TournetAttachmentForm } from "src/types/TournetAttachmentForm";

const user = JSON.parse(localStorage.getItem("user") || "");

export class AttachmentService {
  static getAll(worknum: number): Promise<TournetAttachmentForm[]> {
    return api.get<TournetAttachmentForm[]>(`/attachmentlist/${worknum}`, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }

  static saveTournetDocumentForm(
    worknum: number,
    attachtype: number,
    attachmentFile: FormData
  ): Promise<TournetAttachmentForm> {
    return api.post<TournetAttachmentForm>(
      `/attachmentupdate/${worknum}/${attachtype}`,
      attachmentFile,
      {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${user.accessToken}`, // You need to define 'user' here
        },
      }
    )
      .then((response) => response.data);
  }

  static deleteTournetDocumentForm(fildid: number | any): Promise<void> {
    return api.delete(`/attachmentdeleteFile/${fildid}`, {
      headers: authHeader(),
    })
      .then((response) => response.data);
  }
}
