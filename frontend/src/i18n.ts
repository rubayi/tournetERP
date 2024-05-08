// i18n.ts
import { createI18n } from 'vue-i18n';
import store from "src/store";
let initialLocale = 'ko'; // Default locale

// Check user's permission and set the initial locale
if (store.getters.currentUserHasApplicationPermission("ENG")) {
  initialLocale = 'en';
}

const messages = {
  en: {
    manageEmployees: 'Manage Employees',
    manageCompany: 'Manage Company',
    empNum: 'No',
    username: 'User Name',
    krname: 'Name(Kor)',
    enname: 'Name(Eng)',
    email: 'Email',
    phone: 'Phone',
    divname: 'Department',
    empstatus: 'Employee Status',
    workphone: 'Phone(Work)',
  },
  ko: {
    manageEmployees: '직원 관리',
    manageCompany: '업체 관리',
    empNum: '번호',
    username: '사용자명',
    krname: '이름(한글)',
    enname: '이름(영문)',
    email: '이메일',
    phone: '전화',
    divname: '부서명',
    empstatus: '상태',
    workphone: '전화번호',
  },
};

const i18n = createI18n({
  locale: initialLocale,
  legacy: false,
  globalInjection: true,
  messages,
});

function changeLocale(langFlag: "ko" | "en") {
  i18n.global.locale.value = langFlag;
}

store.watch(
  () => store.getters.currentUserHasApplicationPermission("ENG"),
  (newValue, oldValue) => {
    if (newValue !== oldValue) {
      // Change the locale based on the new permission
      const newLocale = newValue ? 'en' : 'ko';
      changeLocale(newLocale);
    }
  }
);
export default i18n;
