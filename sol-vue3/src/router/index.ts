// import { createRouter, createWebHistory } from 'vue-router'

// const routes = []
// /**
//  * 批量导入 views 下面的所有组件
//  */
// // const viewsContext: Record<string, any> = import.meta.glob('@/views/*.vue')/\.vue$/
// const viewsContext: Record<string, any> = import.meta.glob('@/views/ '+'\\.vue$/')
// for (const path in viewsContext) {
//   const viewName = path.replace(/^.+\/([^/]+)\.vue$/, '$1')
//   const route = {
//     path: `/${viewName.toLowerCase()}`,
//     name: viewName,
//     component: () => import(`@/views/${viewName}.vue`)
//   }
//   routes.push(route)
// }
// const router = createRouter({
//   history: createWebHistory(),
//   routes
// })
import {useUserStore} from '@/stores/user'
// export default router
import { createRouter, createWebHashHistory } from 'vue-router';
// import { setupLayouts } from 'virtual:generated-layouts';//将route里面的路由变成嵌套路由
// import generatedRoutes from 'virtual:generated-pages';
import routes from '~pages'
 
// const routes = setupLayouts(generatedRoutes);


const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach((to :any,from:any,next:any)=>{
	
	const user = useUserStore();
	console.log("路由"+user);
	if (to.path === '/login') return next();
	if(user.token === null && user.tokenHead === null){
		return next({path:'/login'})
		console.log("开始拦截");
	}else{
		return next()  
	}
})

export default router


