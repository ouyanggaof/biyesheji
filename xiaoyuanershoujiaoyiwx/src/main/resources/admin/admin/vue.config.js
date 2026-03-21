// var webpack = require('webpack');
const path = require('path')
function resolve(dir) {
    return path.join(__dirname, dir)
}
function publicPath(){
    if (process.env.NODE_ENV == 'production') {
        return "././";
    } else {
        return "/";
    }
}
// vue.config.js
module.exports = {
    // 关闭并行，降低 Webpack 峰值内存（打包更慢）
    parallel: false,
    productionSourceMap: false,
    // publicPath:"././",
    publicPath: publicPath(),
    // 使用 dart-sass（sass 包），避免 node-sass 需下载原生 binding / vendor
    css: {
        loaderOptions: {
            sass: {
                implementation: require('sass'),
            },
            scss: {
                implementation: require('sass'),
            },
        },
    },
    // 生产构建将 vue / element-ui / echarts 走 CDN（见 public/index.html），避免打爆 Node 堆内存
    configureWebpack: (config) => {
        config.devtool = process.env.NODE_ENV === 'production' ? false : 'source-map'
        if (!config.resolve) config.resolve = {}
        if (!config.resolve.alias) config.resolve.alias = {}
        config.resolve.alias['@'] = resolve('src')
        if (process.env.NODE_ENV === 'production') {
            config.externals = {
                vue: 'Vue',
                'element-ui': 'ELEMENT',
                echarts: 'echarts'
            }
        }
    },
lintOnSave: false,
    devServer: {
        host: "0.0.0.0", //指定使用一个 host。默认是 localhost，这里默认值即可
        port: 8081, //指定端口
        hot: true, // 开启热更新
        https: false, // 是否开启https模式
        proxy: { // 请求代理服务器
            '/xiaoyuanershoujiaoyiwx': { //带上api前缀的
                target: 'http://localhost:8080/xiaoyuanershoujiaoyiwx/', //代理目标地址
                changeOrigin: true,
                secure: false,
                pathRewrite: { // 在发出请求后将/api替换为''空值，这样不影响接口请求
                    '^/xiaoyuanershoujiaoyiwx': ''
                }
            }
        }
    },
chainWebpack(config) {
    // 设置环境变量 ADMIN_NO_MINIFY=1 可关闭 JS 压缩，显著降低 Terser 内存（dist 体积变大）
    if (process.env.NODE_ENV === 'production' && process.env.ADMIN_NO_MINIFY === '1') {
        config.optimization.minimize(false)
    } else if (process.env.NODE_ENV === 'production') {
        config.optimization.minimizer('terser').tap((args) => {
            if (args && args[0]) {
                args[0].parallel = false
            }
            return args
        })
    }
    // 生产环境在打包入口前注入 CDN 脚本（顺序：Vue -> Element -> Echarts）
    config.plugin('html').tap((args) => {
        if (process.env.NODE_ENV === 'production') {
            args[0].externalsScripts = [
                'https://cdn.jsdelivr.net/npm/vue@2.6.10/dist/vue.min.js',
                'https://cdn.jsdelivr.net/npm/element-ui@2.15.10/lib/index.js',
                'https://cdn.jsdelivr.net/npm/echarts@4.9.0/dist/echarts.min.js',
                // 必须在 echarts.min.js 之后；勿在 main.js 里 import echarts/theme/... 否则会整包打进 bundle
                'https://cdn.jsdelivr.net/npm/echarts@4.9.0/theme/macarons.js'
            ]
        }
        return args
    })
    config.module
      .rule('svg')
      .exclude.add(resolve('src/icons'))
      .end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
      .end()
}
}
