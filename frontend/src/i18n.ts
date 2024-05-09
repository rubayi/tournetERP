// i18n.ts
import { createI18n } from 'vue-i18n';
import store from 'src/store';

const initialLocale = store.getters.currentUserHasApplicationPermission('ENG')
  ? 'en'
  : 'ko';
const messages = {
  en: {
    addnew: 'Add New',
    add: 'Add',
    cancel: 'Close',
    delete: 'Delete',
    change: 'Change',
    print: 'Print',
    reset: 'Reset',
    saving: 'Saving...',
    saved: 'Information Saved.',
    export: 'Export',
    search: 'Search',
    searchtitle: 'This is a filter for detailed search for data search.',
    deleteconfirmmsg: 'Are you sure you want to "PERMANENTLY DELETE" this Data?',
    deletesucess: 'Delete completed.',
    manageCodes: 'Manage Codes',
    manageEmployees: 'Manage Employees',
    manageCompany: 'Manage Company',
    primaryiformation: 'Primary Information',
    logofile: 'Logo File',
    num: 'No',
    empNum: 'No',
    emergencyName: 'Name',
    relation: 'Relation',
    emerContact: 'Emergency Contact',
    username: 'User Name',
    krname: 'Name(Kor)',
    enname: 'Name(Eng)',
    email: 'Booking Email',
    phone: 'Phone',
    divname: 'Department',
    empstatus: 'Status',
    workphone: 'Phone(Work)',
    compgroup: 'Group',
    compsector: 'Company Sector',
    estdt: 'Establishment Date',
    krcomp: 'Name(Kor)',
    encomp: 'Name(Eng)',
    abbreviation: 'Abbreviation',
    complvl: 'Company Level',
    assgnprepaid: 'Assign Prepaid',
    couponuseyn: 'Coupon Can Use',
    hotelrate: 'Hotel Rate',
    compreport: 'Company List',
  },
  ko: {
    addnew: '등록',
    add: '등록하기',
    cancel: '닫기',
    delete: '삭제하기',
    change: '수정하기',
    print: '인쇄',
    reset: '초기화',
    saving: '저장중...',
    saved: '저장 되었습니다.',
    export: '다운로드',
    search: '검색',
    searchtitle: '검색',
    deleteconfirmmsg: '정말 삭제 하시겠습니까?',
    deletesucess: '데이터가 삭제 되었습니다.',
    manageCodes: '코드 관리',
    manageEmployees: '직원 관리',
    manageCompany: '업체 관리',
    primaryiformation: '기본정보',
    logofile: '로고 파일',
    num: '번호',
    empNum: '번호',
    emergencyName: '비상연락인 이름',
    relation: '관계',
    emerContact: '비상연락처',
    username: '사용자명',
    krname: '이름(한글)',
    enname: '이름(영문)',
    email: '예약 이메일',
    phone: '전화',
    divname: '부서명',
    empstatus: '상태',
    workphone: '전화번호',
    compgroup: '그룹',
    compsector: '업체형태',
    estdt: '설립일',
    krcomp: '업체명(한글)',
    encomp: '업체명(영문)',
    abbreviation: '약어',
    complvl: '업체등급',
    assgnprepaid: '결제카드지정',
    couponuseyn: '쿠폰 사용 유무',
    hotelrate: '호텔등급',
    compreport: '업체목록',
  },
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
