const path = require('path')
module.exports = {
    dev: {
        //path
        assetsSubDirectory: 'static',
        assetsPublicPath: '/',
        proxyTable: {
            '/test': {
                target: 'http://10.1.7.132:8080',
                changeOrigin: true,
                pathRewrite: {
                    '^/test': '/test'
                }
            }
        },
    }
}