<template>
  <div class="col-md-12">
    <div class="card card-container">
      <img
        id="profile-img"
        src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
        class="profile-img-card"
      />
      <ValidationForm @submit="handleRegister" :validation-schema="schema">
        <div v-if="!successful">
          <div class="form-group">
            <label for="username">Username</label>
            <Field name="username" type="text" class="form-control" />
            <ErrorMessage name="username" class="error-feedback" />
          </div>
          <div class="form-group">
            <label for="email">Email</label>
            <Field name="email" type="email" class="form-control" />
            <ErrorMessage name="email" class="error-feedback" />
          </div>
          <div class="form-group">
            <label for="password">Password</label>
            <Field name="password" type="password" class="form-control" />
            <ErrorMessage name="password" class="error-feedback" />
          </div>

          <div class="form-group">
            <button class="btn btn-primary btn-block" :disabled="loading">
              <span
                v-show="loading"
                class="spinner-border spinner-border-sm"
              ></span>
              Sign Up
            </button>
          </div>
        </div>
      </ValidationForm>

      <div
        v-if="message"
        class="alert"
        :class="successful ? 'alert-success' : 'alert-danger'"
      >
        {{ message }}
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue';
import { Form as ValidationForm, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";
// Store
import {EmpService} from "src/services/EmpService";
import {EmpForm} from "src/types/EmpForm";
export default defineComponent({
  name: "RegisterTN",
  components: {
    ValidationForm,
    Field,
    ErrorMessage,
  },
  setup(props, { emit }) {
    const empformData = ref<EmpForm>(new EmpForm());
  },
  data() {

    const schema = yup.object().shape({
      username: yup
        .string()
        .required("Username is required!")
        .min(3, "Must be at least 3 characters!")
        .max(20, "Must be maximum 20 characters!"),
      email: yup
        .string()
        .required("Email is required!")
        .email("Email is invalid!")
        .max(50, "Must be maximum 50 characters!"),
      password: yup
        .string()
        .required("Password is required!")
        .min(6, "Must be at least 6 characters!")
        .max(40, "Must be maximum 40 characters!"),
    });

    return {
      successful: false,
      loading: false,
      message: "",
      schema,
    };
  },


  methods: {

    handleRegister(empformData:
                     { empUuid: number;
                       username: string;
                       empEmail: string;
                       password: string;
                       empKor: string;
                       empEng: string;
                       empImg: string;
                       empDiv: number;
                       empRole: number;
                       empTitle: number;
                       empPhone: string;
                       empWorkPhone: string;
                       empAddress: string;
                       empDob: string;
                       empEmailBook: string;
                       empBeginDt: string;
                       empMemo: string;
                       empStatus: number;
                       empEndDt: string;
                       empOffice: number;
                       empComp: number;
                       modifiedBy: number;
                       modifiedDt: Date;
                       backColor: string;
                       fontColor: string;
                     }) {
      this.message = "";
      this.successful = false;
      this.loading = true;

      EmpService.createEmpForm(empformData)
        .then((response: EmpForm) => {
          console.log("등록 했습니다.");
         })

    },
  },
});
</script>

<style scoped>
/* Your styles here */
</style>
