// i18n.ts
import { createI18n } from 'vue-i18n';

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
  locale: 'ko',
  legacy: false,
  globalInjection: true,
  messages,
});

export default i18n;
