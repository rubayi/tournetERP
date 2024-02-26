<template>
  <div>
    <h2>ComCode2</h2>
    <p>ComCode is a list of common codes used in the system.</p>
    <p>Here is a list of comcodes:</p>

    <!-- Form for creating and updating comcodes -->
    <form @submit.prevent="saveComCode">
      <!-- Input fields for comcode properties go here -->
      <button type="submit">Save</button>
    </form>

    <!-- List of comcodes -->
    <ul>
      <li v-for="comCode in comCodes" :key="comCode.codeUuid">
        <!-- Display comcode properties here -->
        {{ comCode.codeKr }}
        <button @click="editComCode(comCode)">Edit</button>
        <button @click="deleteComCode(comCode.id)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "ComCode",
  data() {
    return {
      comreq: null,
      comCodes: [],
      // Add a data property for the comcode being edited
      comCode: null,
    };
  },
  methods: {
    // Method for getting all comcodes
    getComCodes() {
      console.log("getComCodes method called"); // This will log a message when the method is called

      this.$store.dispatch("comCode/getComCodeList").then(
        (comCodes) => {
          console.log("getComCodes successful", comCodes); // This will log a message and the fetched comcodes when the request is successful
          this.comCodes = comCodes;
        },
        (error) => {
          console.log("getComCodes failed", error); // This will log a message and the error when the request fails
        }
      );
    },
    // Method for saving a comcode (create or update)
    saveComCode() {
      if (this.comCode.id) {
        // Update existing comcode
        // Replace with your actual API call
        this.$store.dispatch("comCode/update", this.comCode).then(
          () => {
            this.getComCodes();
            this.comCode = null;
          },
          (error) => {
            // Handle error
          }
        );
      } else {
        // Create new comcode
        // Replace with your actual API call
        this.$store.dispatch("comCode/create", this.comCode).then(
          () => {
            this.getComCodes();
            this.comCode = null;
          },
          (error) => {
            // Handle error
          }
        );
      }
    },
    // Method for editing a comcode
    editComCode(comCode) {
      this.comCode = Object.assign({}, comCode);
    },
    // Method for deleting a comcode
    deleteComCode(id) {
      // Replace with your actual API call
      this.$store.dispatch("comCode/delete", id).then(
        () => {
          this.getComCodes();
        },
        (error) => {
          // Handle error
        }
      );
    },
  },
  created() {
    this.getComCodes();
  },
};
</script>

<style scoped>
/* Your component styles go here */
</style>
