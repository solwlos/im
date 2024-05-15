import { fileURLToPath, URL } from 'node:url'

import { defineConfig, ProxyOptions } from 'vite'
import vue from '@vitejs/plugin-vue'

import vueJsx from '@vitejs/plugin-vue-jsx'
import Pages from 'vite-plugin-pages'
// import Layouts from 'vite-plugin-vue-layouts'

import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
 

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueJsx(),
    Pages({
      dirs: 'src/views',
      extensions: ['vue'],
    }),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  resolve: {
    extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue'],
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      // '~/': `${path.resolve(__dirname, 'src')}/`,
    },
    
  },
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@use "~/styles/element/index.scss" as *;`,
      },
    },
  },
  base: "./",
  server: { 
    host: true, 
    port: 9090, //自己服务器的端口号
    headers: {
      'Access-Control-Allow-Origin': '*',
    },
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8080', 
        // target: 'http://localhost:8080', 
        changeOrigin: true,       //是否跨域
        ws: false,            //是否代理 websockets
        secure: false,          //是否https接口
        rewrite: (path: string) => path.replace(/^\/api/, ''), // 不可省略 
        bypass(req, res, options: ProxyOptions){

          const proxyUrl = new URL(options.rewrite!(req.url || '') || '', (options.target) as string)?.href || '';
          req.headers["x-req-proxyUr1"] = proxyUrl;// 设置无效
          res.setHeader("x-res-proxyUrl",proxyUrl);// 有效
        },
      }
    }
  },
  
})
