import {createRouter, createWebHistory} from 'vue-router'

const Test = () =>
  import ( /* webpackChunkName: "login_home_welcome" */ '../components/Test.vue')
const Login = () =>
  import ( /* webpackChunkName: "login_home_welcome" */ '../components/Login.vue')
const Home = () =>
  import ( /* webpackChunkName: "login_home_welcome" */ '../components/Home.vue')
const Analyze = () =>
  import ( /* webpackChunkName: "login_home_welcome" */ '../components/Analyze.vue')
const Interface = () =>
  import ( /* webpackChunkName: "login_home_welcome" */ '../components/Interface.vue')
const Hi = () =>
  import ( /* webpackChunkName: "login_home_welcome" */ '../components/Hi.vue')
const routes = [
  {path: '/', redirect: '/login'},
  {path: '/login', component: Login},
  {path: '/home', component: Home},
  {
    path: '/home',
    component: Home,
    // redirect: '/home',
    children: [

      {path: '/analyze', component: Analyze},
      {path: '/Interface', component: Interface},
      // {path: '/interfaceDetails', component: interfaceDetails},
      // {path: '/PageInterface', component: PageInterface},
      // {path: '/Article', component: Article},
      // { path: '/general', component: General },
      // { path: '/logs', component: Logs },
      // { path: '/buglist', component: Buglist },
      // { path: '/parameter', component: Parameter },
      // { path: '/crack', component: Crack },
      // { path: '/upload', component: Upload },
      // { path: '/view', component: View }
      {path: '/hi', component: Hi}
    ]
  },
  {
    path: '/test',
    component: Test,
    children: [{path: '/hi', component: Hi},]
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})
// 暂时关闭重定向
router.beforeEach((to, from, next) => {
  if (to.path === '/login') return next();
  const tokenStr = localStorage.getItem('userInfo')
  // const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) return next('/login')
  next()
})
export default router
