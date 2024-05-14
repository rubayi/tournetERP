// i18n.ts
import { createI18n } from 'vue-i18n';
import store from 'src/store';
import en from 'src/en.json';
import ko from 'src/ko.json';

const initialLocale = store.getters.currentUserHasApplicationPermission('ENG')
  ? 'en'
  : 'ko';
const messages = {
  en,
  ko,
};

const i18n = createI18n({
  locale: initialLocale,
  legacy: false,
  globalInjection: true,
  messages,
});

function changeLocale(langFlag: 'ko' | 'en') {
  i18n.global.locale.value = langFlag;
}

store.watch(
  () => store.getters.currentUserHasApplicationPermission('ENG'),
  (newValue, oldValue) => {
    if (newValue !== oldValue) {
      const newLocale = newValue ? 'en' : 'ko';
      changeLocale(newLocale);
    }
  }
);
export default i18n;
