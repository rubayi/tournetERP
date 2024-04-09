import Home from "components/Homehome.vue";
import Login from "components/LoginTN.vue";
import EmpList from "src/views/emp/EmpTnList.vue";
import CompList from "src/views/comp/CompList.vue";
import TourList from "src/views/tour/TourList.vue";
import CdcdList from "src/views/cdcd/CdcdList.vue";
import Register from "components/RegisterTN.vue";
import ComCode from "components/ComCode.vue";
import ComMenu from "components/ComMenu.vue";
import UploadFiles from "components/common/UploadFiles.vue";

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
    path: "/tourList/:sector",
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => TourList }
    ]
  },
  {
      path: "/cdcdList",
      component: () => import('layouts/MainLayout.vue'),
      children: [
          { path: '', component: () => CdcdList }
      ]
  },
  {
      path: "/profile",
      name: "profile",
      component: Profile,
  },
  {
    path: "/uploadtest",
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => UploadFiles }
    ]
  },
]
export default routes
