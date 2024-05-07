// i18n.ts
import { createI18n } from 'vue-i18n';

const messages = {
  en: {
    manageEmployees: 'Manage Employees22',
  },
  ko: {
    manageEmployees: '직원 관리',
  },
};

const i18n = createI18n({
  locale: 'en',
  legacy: false,
  globalInjection: true,
  messages,
});

export default i18n;
