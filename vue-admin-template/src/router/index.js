import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {  //   matched = [{ path: '/dashboard', meta: { title: 'Dashboard' }}].concat(matched)
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页O', icon: 'dashboard' }
    }]
  },
  // {
  //   path: '/rbac',
  //   component: Layout,
  //   redirect: '/rbac/user',
  //   meta: { title: '系统管理', icon: 'example' },
  //   children: [
  //     {
  //       path: '/rbac/user',
  //       name: 'rbac_user',
  //       component: () => import('@/views/rbac/user'),
  //       meta: { title: '用户管理', icon: 'example' }
  //     },
  //     {
  //       path: '/rbac/role',
  //       name: 'rbac_role',
  //       component: () => import('@/views/rbac/role'),
  //       meta: { title: '角色管理', icon: 'example' }
  //     },
  //     {
  //       path: '/rbac/resource',
  //       name: 'rbac_resource',
  //       component: () => import('@/views/rbac/resource'),
  //       meta: { title: '资源管理', icon: 'example' }
  //     }
  //   ]
  // },
  {
    path: '/form',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('@/views/form/index'),
        meta: { title: 'Form', icon: 'form' }
      }
    ]
  },

  {
    path: '/nested',
    component: Layout,
    redirect: '/nested/menu1',
    name: 'Nested',
    meta: {
      title: 'Nested',
      icon: 'nested'
    },
    children: [
      {
        path: 'menu1',
        component: () => import('@/views/nested/menu1/index'), // Parent router-view
        name: 'Menu1',
        meta: { title: 'Menu1' },
        children: [
          {
            path: 'menu1-1',
            component: () => import('@/views/nested/menu1/menu1-1'),
            name: 'Menu1-1',
            meta: { title: 'Menu1-1' }
          },
          {
            path: 'menu1-2',
            component: () => import('@/views/nested/menu1/menu1-2'),
            name: 'Menu1-2',
            meta: { title: 'Menu1-2' },
            children: [
              {
                path: 'menu1-2-1',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
                name: 'Menu1-2-1',
                meta: { title: 'Menu1-2-1' }
              },
              {
                path: 'menu1-2-2',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
                name: 'Menu1-2-2',
                meta: { title: 'Menu1-2-2' }
              }
            ]
          },
          {
            path: 'menu1-3',
            component: () => import('@/views/nested/menu1/menu1-3'),
            name: 'Menu1-3',
            meta: { title: 'Menu1-3' }
          }
        ]
      },
      {
        path: 'menu2',
        component: () => import('@/views/nested/menu2/index'),
        meta: { title: 'menu2' }
      }
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        meta: { title: 'External Link', icon: 'link' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  // { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})


const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}


export const loadView = (url) => { // 路由懒加载
  // 为corn 玉米产量特殊设置的路由文件 // 玉米的菜单url必须为/corn-前缀:  如： /corn-huabei/beijing 格式
  if(url.startsWith("/corn-")){
    return () => import(`@/views/corn/common.vue`);
  }else{
    return () => import(`@/views${url}.vue`);
  }
}

// 添加权限
export function createMenus(data) {
  let trees = data[0].children;  // 获取/ 下的所有子元素
  function loopChildren(children){
    if(children == '' || children.length == 0) return ;
    for(let i in children){
      let item = children[i];
      // const _import = require('./router/_import_' + process.env.NODE_ENV)
      // resolve => require(['../pages/home.vue'], resolve),
      if(item.level === 'Dir_Module'){
        children[i] = {
          path: item.url, name:item.url,
          component: Layout,
          meta: { title: item.name, icon: 'example' }
        };
        let sub = item.children;
        loopChildren(sub);
        if(sub){
          children[i].children = sub;
        }
      }else{ // Menu_Entity
        console.log('@/views' + item.url + '.vue')
        children[i] = {
          path: item.url, name:item.url,
          component: loadView(item.url),
          meta: { title: item.name, icon: 'example' }
        };
      }
    }
  }
  loopChildren(trees)
  console.log('trees',trees)
  router.options.routes = trees.concat(constantRoutes);
  router.options.routes.push({ path: '*', redirect: '/404', hidden: true })
  router.addRoutes(trees) // 动态添加可访问路由表
}


export default router
