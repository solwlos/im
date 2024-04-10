/* eslint-env node */
require('@rushstack/eslint-patch/modern-module-resolution')

module.exports = {
  root: true,
  'extends': [
    'plugin:vue/vue3-essential',
    'eslint:recommended',
    '@vue/eslint-config-typescript',
    '@vue/eslint-config-prettier/skip-formatting',
    '@vue/prettier',
    '@vue/prettier/@typescript-eslint',
    './.eslintrc-auto-import.json', //在这里配置生成的JSON文件 需要和vite.config.ts文件保持一致
  ],
  parserOptions: {
    ecmaVersion: 'latest'
  }
}
