import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [

    {
        path: '/login',
        name: 'Login',
        component: () => import('../common/Login.vue')

    },
    {
        path: '/404',
        name: '404',
        component: () => import('../views/404.vue')

    },
    {//前台
        path: '/front',
        name: 'frontHome',
        redirect: '/front/home',
        component: () => import('../views/front/Front.vue'),
        children: [
            {//前台主页
                path: 'home',
                name: 'FrontHome',
                component: () => import('../views/front/Home.vue'),
            },
            {//个人信息
                path: 'personInfo',
                name: 'PersonInfo',
                component: () => import('../views/front/PersonInfo.vue'),
            },
            {//查看预约信息
                path: 'resInfos',
                name: 'ResInfos',
                component: () => import('../views/front/ResInfos.vue'),
            },
            {//答疑预约
                path: 'selectTeachers',
                name: 'SelectTeachers',
                component: () => import('../views/front/SelectTeachers.vue'),
            },
            {//文章列表
                path: 'article',
                name: 'Article',
                component: () => import('../views/front/Article.vue'),

            },
            {//文章详情
                path: 'articleDetail',
                name: 'ArticleDetail',
                component: () => import('../views/front/ArticleDetail.vue'),

            },{
            path: 'updatePassword',
                name:'UpdatePassword',
                component:()=>import('../views/front/UpdatePassword.vue')
            }
        ]

    }

]

//解决编程式路由往同一地址跳转时会报错的情况
const originalPush = VueRouter.prototype.push
const originalReplace = VueRouter.prototype.replace
//push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
    if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
    return originalPush.call(this, location).catch(err => err)
}
//replace
VueRouter.prototype.replace = function push(location, onResolve, onReject) {
    if (onResolve || onReject) return originalReplace.call(this, location, onResolve, onReject)
    return originalReplace.call(this, location).catch(err => err)

}
const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

//提供一个重置路由方法
export const resetRouter = () => {
    router.matcher = new VueRouter({
        mode: 'history',
        base: process.env.BASE_URL,
        routes
    })
}


export const setRoutes = () => {
    const storeMenus = localStorage.getItem("menus");
    if (storeMenus) {
        //拼装动态路由
        const manageRoute = {
            path: '/',
            name: 'Manage',
            component: () => import('../views/Manage.vue'),
            redirect: '/home',
            children: [
                {
                    path: 'personInfo', name: '个人信息', component: () => import('../views/PersonInfo.vue')
                },
                {//修改密码
                    path: 'updatePassword',
                    name: '修改密码',
                    component: () => import('../views/UpdatePassword.vue'),

                }
            ]
        }
        const menus = JSON.parse(storeMenus);
        menus.forEach(item => {
            if (item.path) {//当且仅当path不为空时
                let itemMenu = {
                    path: item.path.replace('/', ''),
                    name: item.name,
                    component: () => import('../views/' + item.pagePath + '.vue')
                }
                manageRoute.children.push(itemMenu);
            } else if (item.children.length) {
                item.children.forEach(item => {
                    if (item.path) {
                        let itemMenu = {
                            path: item.path.replace('/', ''),
                            name: item.name,
                            component: () => import('../views/' + item.pagePath + '.vue')
                        }
                        manageRoute.children.push(itemMenu);
                    }
                })
            }
        })
        //将路径名存起来
        const currentPathRouteNames = router.getRoutes().map(value => value.name)

        //当路由数组中不包含当前路由对象时 动态添加 避免重复 节省内存
        if (!currentPathRouteNames.includes('Manage')) {
            //动态添加到现在的路由对象中去
            router.addRoute(manageRoute);
        }

    }
}

//刷新时重置路由
setRoutes()

// 路由守卫
router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
    store.commit("setPath")  // 触发store的数据更新

    //未找到路由的情况
    if (!to.matched.length) {
        const storeMenus = localStorage.getItem('menus');
        if (storeMenus) {
            next('/404')  //跳转到404
        } else {
            //跳回登录界面
            next('/login')
        }
    }

    next();// 放行路由

})

export default router
