import axios from 'axios';
import { ElMessage, ElLoading } from 'element-plus';



// 创建axios实例
const service = axios.create({
    // 服务接口请求
    baseURL: '/api',
    // 超时设置
    // timeout: 15000,
    headers:{'Content-Type':'application/json;charset=utf-8'}
})

// 添加请求拦截器
service.interceptors.request.use(function (config) {
    console.log("拦截成功");
    showLoading();
    // 在发送请求之前做些什么
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

// 添加响应拦截器
service.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    return response;
  }, function (error) {
    console.log('err' + error)
    hideLoading()
    let { message } = error;
    if (message == "Network Error") {
        message = "后端接口连接异常";
    }
    else if (message.includes("timeout")) {
        message = "系统接口请求超时";
    }
    else if (message.includes("Request failed with status code")) {
        message = "系统接口" + message.substr(message.length - 3) + "异常";
    }
    ElMessage.error({
        message: message,
        duration: 5 * 1000
    })
    return Promise.reject(error)
  });

// // 创建axios实例
// const service = axios.create({
//     // 服务接口请求
//     baseURL: import.meta.env.VITE_APP_BASE_API,
//     // 超时设置
//     // timeout: 15000,
//     headers:{'Content-Type':'application/json;charset=utf-8'}
// })

// let loading:any;
// //正在请求的数量
// let requestCount:number = 0
// //显示loading
const showLoading = () => {
    if (requestCount === 0 && !loading) {
        //加载中显示样式可以自行修改
        loading = ElLoading.service({
            text: "拼命加载中，请稍后...",
            background: 'rgba(0, 0, 0, 0.7)',
            spinner: 'el-icon-loading',
        })
    }
    requestCount++;
}
//隐藏loading
const hideLoading = () => {
    requestCount--
    if (requestCount == 0) {
        loading.close()
    }
}

// // 请求拦截
// service.interceptors.request.use(config => {
//     showLoading()
//     // 是否需要设置 token放在请求头
//     config.headers['Authorization'] = 'Bearer ' + getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
//     // get请求映射params参数
//     if (config.method === 'get' && config.params) {
//         let url = config.url + '?';
//         for (const propName of Object.keys(config.params)) {
//             const value = config.params[propName];
//             const part = encodeURIComponent(propName) + "=";
//             if (value !== null && typeof(value) !== "undefined") {
//                  // 对象处理
//                 if (typeof value === 'object') {
//                     for (const key of Object.keys(value)) {
//                         const params = propName + '[' + key + ']';
//                         const subPart = encodeURIComponent(params) + "=";
//                         url += subPart + encodeURIComponent(value[key]) + "&";
//                     }
//                 } else {
//                     url += part + encodeURIComponent(value) + "&";
//                 }
//             }
//         }
//         url = url.slice(0, -1);
//         config.params = {};
//         config.url = url;
//     }
//     return config
// }, error => {
//     console.log(error)
//     Promise.reject(error)
// })

// // 响应拦截器
// service.interceptors.response.use((res:any) => {
//         hideLoading()
//         // 未设置状态码则默认成功状态
//         const code = res.data['code'] || 200;
//         // 获取错误信息
//         const msg = errorCodeType(code) || res.data['msg'] || errorCodeType('default')
//         if(code === 200){
//             return Promise.resolve(res.data)
//         }else{
//             ElMessage.error(msg)
//             return Promise.reject(res.data)
//         }
//     },
//     error => {
//         console.log('err' + error)
//         hideLoading()
//         let { message } = error;
//         if (message == "Network Error") {
//             message = "后端接口连接异常";
//         }
//         else if (message.includes("timeout")) {
//             message = "系统接口请求超时";
//         }
//         else if (message.includes("Request failed with status code")) {
//             message = "系统接口" + message.substr(message.length - 3) + "异常";
//         }
//         ElMessage.error({
//             message: message,
//             duration: 5 * 1000
//         })
//         return Promise.reject(error)
//     }
// )

export default service;