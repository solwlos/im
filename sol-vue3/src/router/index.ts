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
import { createRouter, createWebHashHistory } from 'vue-router';
import routes from '~pages'

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach((to :any,from:any,next:any)=>{
	
	const user = useUserStore();

	console.log(to.path + " -> " + user.isAuthenticated())

	if (to.path === '/login') return next();
	if(user.isAuthenticated()){

		return next({path:'/login'})
	}else{
		return next()  
	}
})

export default router


