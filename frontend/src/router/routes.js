import Home from "components/Homehome.vue";
import Login from "components/LoginTN.vue";
import EmpList from "src/views/emp/EmpTnList.vue";
import CompList from "src/views/comp/CompList.vue";
import Register from "components/RegisterTN.vue";
import ComCode from "components/ComCode.vue";
import ComMenu from "components/ComMenu.vue";

const Profile = () => import("../components/Profile.vue")

const routes = [
  {
    path: "/",
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => Login }
    ]
  },
  {
    path: "/home",
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => Login }
    ]
  },
  {
    path: "/login",
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => Login }
    ]
  },
  {
    path: "/comcode",
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '/comcode', component: () => ComCode }
    ]
  },
  {
    path: "/comMenu",
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '/comMenu', component: () => ComMenu }
    ]
  },
  {
    path: "/register",
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => Register }
    ]
  },
  {
    path: "/empList",
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => EmpList }
    ]
  },
  {
    path: "/compList",
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => CompList }
    ]
  },
  {
      path: "/profile",
      name: "profile",
      component: Profile,
  },

]
export default routes
