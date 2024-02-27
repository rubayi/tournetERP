<template>
  <div>
    <h3>코드관리</h3>
    <div class="grid-container">
      <div>
        <div class="toc">
          <ul class="list no-bullets">
            <li v-for="comCode in comCodes" :key="comCode.codeUuid">
              <button class="" @click="chooseMainComCode(comCode)">
                {{ comCode.codeKr }}
              </button>
            </li>
          </ul>
        </div>
      </div>
      <div>
        <table>
          <thead>
            <tr>
              <th>Code UUID</th>
              <th>Code EN</th>
              <th>Code KR</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="comCode in chosenComCodes" :key="comCode.codeUuid">
              <td>{{ comCode.codeUuid }}</td>
              <td>{{ comCode.codeEn }}</td>
              <td>{{ comCode.codeKr }}</td>
              <td>
                <button @click="editComCode(comCode)">Edit</button>
                <button @click="deleteComCode(comCode.id)">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
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
      chosenComCodes: [],
    };
  },
  methods: {
    getComCodes() {
      this.$store.dispatch("comCode/getComCodeList").then(
        (comCodes) => {
          this.comCodes = comCodes;
        },
        (error) => {
          console.log("getComCodes failed", error);
        }
      );
    },
    getMainComCode() {
      this.$store.dispatch("comCode/getMainComCodeList").then(
        (comCodes) => {
          this.comCodes = comCodes;
          this.chosenComCodes = comCodes;
        },
        (error) => {
          console.log("getMainComCode failed", error);
        }
      );
    },
    saveComCode() {
      if (this.comCode.id) {
        // Replace with your actual API call
        this.$store.dispatch("comCode/update", this.comCode).then(
          () => {
            this.getComCodes();
            this.comCode = null;
          },
          (error) => {
            console.log("saveComCode failed", error);
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
            console.log("saveComCode failed", error);
          }
        );
      }
    },
    // Method for editing a comcode
    editComCode(comCode) {
      this.comCode = Object.assign({}, comCode);
    },
    chooseMainComCode(comCode) {
      this.$store.dispatch("comCode/SearchComCodeListByGrp", comCode).then(
        (comcodes) => {
          this.chosenComCodes = comcodes;
        },
        (error) => {
          console.log("chooseMainComCode failed", error);
        }
      );
    },
    // Method for deleting a comcode
    deleteComCode(id) {
      // Replace with your actual API call
      this.$store.dispatch("comCode/delete", id).then(
        () => {
          this.getComCodes();
        },
        (error) => {
          console.log("deleteComCode failed", error);
        }
      );
    },
  },
  created() {
    this.getMainComCode();
  },
};
</script>

<style scoped>
.grid-container {
  display: grid;
  grid-template-columns: 20% 50% 30%;
  gap: 20px;
}
.no-bullets {
  list-style-type: none;
  border: 1px solid #b9b8b8;
}
.toc {
  border: 1px solid #b9b8b8;
  padding: 10px;
}

.list {
  margin: 0;
  padding: 0;
}

.list > li {
  padding: 10px 0; /* Adjust as needed */
  border-bottom: 1px solid #000;
}

.list > li:last-child {
  border-bottom: none;
}

.plain-button {
  background: none;
  border: none;
  padding: 0;
  text-align: left;
  width: 100%;
  text-decoration: none;
  color: inherit;
  display: block;
}
</style>
