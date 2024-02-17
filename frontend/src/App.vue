<template>
  <q-layout view='hHh Lpr hFf'>
    <q-header elevated class="bg-grey-3 text-blue-grey">
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          icon='menu'
          aria-label='Menu'
          @click='leftDrawerOpen = !leftDrawerOpen'
        />

        <q-toolbar-title>
          <img src="images/logo_tnh2.png"
               style="width: 100px; height: 53px"/>
        </q-toolbar-title>
        <div>
          <q-btn
            v-if='!$store.state.auth.user'
            flat
            to='/register'
            stretch
            class='text-bold'
          ><q-icon
            name='fas fa-user-plus'
            class='q-mr-sm text-white'
            size='xs'
          ></q-icon
          >Register</q-btn
          >
          <q-btn
            v-if='!$store.state.auth.user'
            to='/login'
            flat
            stretch
            class='text-bold'
          >
            <q-icon
              name='fas fa-sign-in-alt'
              class='q-mr-sm text-white'
              size='xs'
            ></q-icon
            >Login</q-btn
          >
          <q-btn
            v-if='$store.state.auth.user'
            to='/profile'
            flat
            stretch
            class='text-bold'
          ><q-icon
            name='fas fa-user-circle'
            class='q-mr-sm text-white'
            size='xs'
          ></q-icon
          > {{ currentUser.username }}</q-btn
          >
          <q-btn v-if='isAdmin' to='/admin' flat stretch class='text-bold'
          ><q-icon
            name='fas fa-chart-line'
            class='q-mr-sm text-white'
            size='xs'
          ></q-icon
          >Admin Dashboard</q-btn
          >

          <q-btn
            v-if='$store.state.auth.user'
            @click='logout'
            flat
            stretch
            class='text-bold'
          >
            <q-icon
              name='fas fa-sign-out-alt'
              class='q-mr-sm text-white'
              size='xs'
            ></q-icon
            >Logout</q-btn
          >
        </div>
      </q-toolbar>
    </q-header>

    <q-drawer
      v-model='leftDrawerOpen'
      show-if-above
      content-class='bg-grey-1'
    >
      <q-list>
        <EssentialLink
          v-for='link in essentialLinks'
          :key='link.title'
          v-bind='link'
        />
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>

    <q-footer elevated class="bg-grey-8 text-white">
      <q-toolbar>
        <q-toolbar-title>
          <q-avatar>

          </q-avatar>
          <div></div>
        </q-toolbar-title>
      </q-toolbar>
    </q-footer>
  </q-layout>
</template>

<script>
import EssentialLink from 'components/EssentialLink.vue'
import { useQuasar } from 'quasar'

const linksData = [
  {
    title: '현황판',
    caption: '',
    icon: 'feed',
    link: 'https://quasar.dev'
  },
  {
    title: '공지사항',
    caption: '',
    icon: 'record_voice_over',
    link: 'https://github.com/quasarframework'
  },
  {
    title: '예약',
    caption: '신규예약/예약조회',
    icon: 'calendar_month',
    link: 'https://chat.quasar.dev'
  },
  {
    title: '회계',
    caption: '정산관리/쿠폰관리',
    icon: 'savings',
    link: 'https://forum.quasar.dev'
  },
  {
    title: '업무리포트',
    caption: '',
    icon: 'trending_up',
    link: 'https://twitter.quasar.dev'
  },
  {
    title: '상품관리',
    caption: '기본 상품관리',
    icon: 'widgets',
    link: 'https://facebook.quasar.dev'
  },
  {
    title: '가격/휴업관리',
    caption: '가격관리/각상품리포트관리',
    icon: 'attach_money',
    link: 'https://awesome.quasar.dev'
  },
  {
    title: '차량/비품관리',
    caption: '',
    icon: 'commute',
    link: 'https://awesome.quasar.dev'
  },
  {
    title: '직원',
    caption: '직원조회/휴일,당직조회/업체관리',
    icon: 'people',
    link: 'https://awesome.quasar.dev'
  },
  {
    title: '설정',
    caption: '공통코드관리/세율관리',
    icon: 'miscellaneous_services',
    link: 'https://awesome.quasar.dev'
  },
  {
    title: '요청게시판',
    caption: '시스템요청 게시판',
    icon: 'help_outline',
    link: 'https://awesome.quasar.dev'
  }
]

export default {
  name: 'MainLayout',
  components: { EssentialLink },
  setup () {
    const $q = useQuasar()

    // calling here; equivalent to when component is created
    $q.dark.set(true)
  },
  data () {
    return {
      leftDrawerOpen: false,
      essentialLinks: linksData
    }
  },
  computed: {
    isAdmin () {
      const user = this.$store.state.auth.user
      if (user) {
        if (user.permissions) {
          return user.permissions.includes('admin')
        }
      }
      return false
    },
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  methods: {
    logout () {
      this.$store.dispatch('auth/logout')
      this.$router.push('/login')
    }
  }
}
</script>
