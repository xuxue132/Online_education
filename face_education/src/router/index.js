import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  { path: "/", redirect: "/login",meta: {requireAuth: true}},
  { path: "/login",name:'login', component: () => import('@/components/Login/login')},
  { path: "/register",name:'register', component: () => import('@/components/Login/Register') },
  { path: "/adminLogin",name:'AdminLogin', component: () => import('@/components/Login/AdminLogin') },
  { path: "/MyInformation",name:'MyInformation', component: () => import('@/components/MainBody/MyInformation')},
  { path: "/HistoryPage",name:'HistoryPage', component: () => import('@/components/MainBody/HistoryPage')},
  { path: "/NewsPage",name:'NewsPage', component: () => import('@/components/MainBody/NewsPage')},
  { path: "/NoticePage",name:'NoticePage', component: () => import('@/components/MainBody/NoticePage')},
  { path: "/NewText",name:'NewText', component: () => import('@/components/MainBody/NewText')},
  { path: "/ReadPage",name:'ReadPage', component: () => import('@/components/MainBody/ReadPage')},
  { path: "/ReadBookContent",name:'ReadBookContent', component: () => import('@/components/MainBody/ReadBookContent')},
  { path: "/ReadText",name:'ReadText', component: () => import('@/components/MainBody/ReadText')},
  { path: "/CharacteristicProject",name:'CharacteristicProject', component: () => import('@/components/MainBody/CharacteristicDisplay/CharacteristicProject')},
  { path: "/CharacterText",name:'CharacterText', component: () => import('@/components/MainBody/CharacteristicDisplay/CharacterText')},
  { path: "/ExaminationPage",name:'ExaminationPage', component: () => import('@/components/MainBody/Examination/ExaminationPage')},
  { path: "/ExaminationText",name:'ExaminationText', component: () => import('@/components/MainBody/Examination/ExaminationText')},
  { path: "/PrizePage",name:'PrizePage', component: () => import('@/components/MainBody/Examination/PrizePage')},
  { path: "/PrizeText",name:'PrizeText', component: () => import('@/components/MainBody/Examination/PrizeText')},
  { path: "/HonorDisplay",name:'HonorDisplay', component: () => import('@/components/MainBody/HonorDisplay/HonorDisplay')},
  { path: "/HonorText",name:'HonorText', component: () => import('@/components/MainBody/HonorDisplay/HonorText')},
  { path: "/FirstPage",name:'FirstPage', component: () => import('@/components/MainBody/FirstPage')},
  { path: "/FilePage",name:'FilePage', component: () => import('@/components/MainBody/File/FilePage')},
  { path: "/FileText",name:'FileText', component: () => import('@/components/MainBody/File/FileText')},
  {
    path: "/AdminPage",
    name:'AdminPage',
    component: () => import('@/components/MainBody/Admin/AdminPage'),
    meta: {
      auth: true,
    },
    redirect: '/UserPage',
    children: [
      {
        path: '/UserPage',
        component: () => import('@/components/MainBody/Admin/UserPage')
      },
      {
        path: '/NewNoticePage',
        component: () => import('@/components/MainBody/Admin/NewNoticePage')
      },
      {
        path: '/CharacterPage',
        component: () => import('@/components/MainBody/Admin/CharacterPage')
      },
      {
        path: '/Examination',
        component: ()=> import('@/components/MainBody/Admin/Examination')
      },
      {
        path: '/prize',
        component: () => import('@/components/MainBody/Admin/prize')
      },
      {
        path:'/honor',
        component: () => import('@/components/MainBody/Admin/honor')
      },
      {
        path:'/file',
        component: () => import('@/components/MainBody/Admin/file')
      },
      {
        path:'/Book',
        component: () => import('@/components/MainBody/Admin/Book')
      },
    ]
  },
]

const router = new VueRouter({
  mode: "history",
  routes
})

export default router



