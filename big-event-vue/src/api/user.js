import request from '@/utils/request.js'

// 用于注册接口的函数
export const userRegisterService = (registerData)=>{
    const params = new URLSearchParams()
    // URLSearchParams用于构建一个包含多个参数的查询字符串
    for (let key in registerData) {
        params.append(key, registerData[key]);
    }
    // 返回的是请求得到的数据
    return request.post('/user/register', params);
}