import Home from "components/Homehome.vue";
import Login from "components/LoginTN.vue";
import EmpList from "src/views/emp/EmpTnList.vue";
import Register from "components/RegisterTN.vue";
import ComCode from "components/ComCode.vue";
import ComMenu from "components/ComMenu.vue";


const Profile = () => import("../components/Profile.vue")
const BoardAdmin = () => import("../components/BoardAdmin.vue")
const BoardModerator = () => import("../components/BoardModerator.vue")
const BoardUser = () => import("../components/BoardUser.vue")

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
    path: "/members",
    name: "Member",
    component: () => import("../views/member/AppBody.vue"),
  },
  {
    path: "/profile",
    name: "profile",
    component: Profile,
  },
  {
    path: "/admin",
    name: "admin",
    component: BoardAdmin,
  },
  {
    path: "/mod",
    name: "moderator",
    // lazy-loaded
    component: BoardModerator,
  },
  {
    path: "/user",
    name: "user",
    // lazy-loaded
    component: BoardUser,
  },
]
export default routes
