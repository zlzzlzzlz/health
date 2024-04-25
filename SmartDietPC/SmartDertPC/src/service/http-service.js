import axios from 'axios'
//import store from '../store'
import router from '../router';
import {
    Message
} from 'element-ui'
//正式环境
import {
    devUrl
} from '../common/config/dev.env'
//测试环境
// import {
//     prodUrl
// } from '../common/config/prod.env'
//json数据序列化
import qs from 'qs'

//线上环境配置axios.defaults.baseURL
if (process.env.NODE_ENV === 'production') {
    axios.defaults.baseURL = prodUrl;
} else {
    axios.defaults.baseURL = devUrl;
}
//重新封装axios方法
export default {
    post(url, params) {
        return axios({
            method: 'post',
            url: `${url}`,
            data: qs.stringify(params) || {}
        });
    },
    get(url, params) {
        let tempParams = {
            ...arguments(params || {}),
            //解决IE缓存添加一个随机时间戳
            _timer: +(new Date())
        }
        return axios({
            method: 'get',
            params: tempParams,
            url: `${url}`
        });
    },
    put(rul, params) {
        return axios({
            method: 'put',
            url: `${url}`,
            data: qs.stringify(params) || {}
        });
    },
    delete(url, params) {
        return axios({
            method: 'delete',
            params: params || {},
            url: `${url}`
        });
    }
}
