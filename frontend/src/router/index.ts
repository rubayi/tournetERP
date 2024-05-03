import {
  createRouter,
  RouteRecordRaw,
  createWebHashHistory,
  NavigationGuardNext,
  RouteLocationNormalized,
} from 'vue-router';
import store from 'src/store';
import { LoginService } from 'src/services/LoginService';
import Code from 'src/views/code/AppBody.vue';
import Comp from 'src/views/comp/AppBody.vue';
import Cdcd from 'src/views/cdcd/AppBody.vue';
import Emp from 'src/views/emp/AppBody.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: () => import('src/views/Home.vue'),
  },
  {
    path: '/comp',
    name: 'comp',
    children: [{ path: '', component: () => Comp }],
  },
  {
    path: '/temp',
    name: 'temp',
    component: () => import('src/views/temp/AppBody.vue'),
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('src/views/Login.vue'),
  },
  {
    path: '/main',
    name: 'Main',
    component: () => import('src/views/main/AppBody.vue'),
  },
  {
    path: '/comcode',
    name: 'comcode',
    children: [{ path: '', component: () => Code }],
  },
  {
    path: '/cdcd',
    name: 'cdcd',
    children: [{ path: '', component: () => Cdcd }],
  },
  {
    path: '/emp',
    name: 'emp',
    children: [{ path: '', component: () => Emp }],
  },
  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('src/views/NotFound.vue'),
  },
];
const router = createRouter({
  history: createWebHashHistory(),
  routes,
});
router.beforeEach((to, from, next) => {
  loginGuard(to, from, next);
});

function loginGuard(
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext
): void {
  if (to.name !== 'Login') {
    LoginService.getCurrentUser()
      .then((result) => {
        store.commit('updateCurrentUser', result);
        next();
      })
      .catch(() => {
        store.commit('clearCurrentUser');
        if (from.name !== 'Login') {
          setTimeout(() => {
            next({ name: 'Login' });
          }, 2500);
        } else {
          next({ name: 'Login' });
        }
      });
  } else {
    next();
  }
}
export default router;
