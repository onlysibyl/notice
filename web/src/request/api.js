import axios from 'axios';
import WeCom from './WeCom';

import apiRequest from './request';

// 导出调用企业微信机器人的 API
export const PostWeComRobot = (params) => WeCom.post('', params);

// 导出获取测试数据的 GET 请求 API
export const GetTestAPI = (params) => apiRequest.get('/test', { params });

// 导出提交测试数据的 POST 请求 API
export const PostTestAPI = (params) => apiRequest.post('/test', params);

// 导出登录的 POST 请求 API
export const LoginAPI = (params) => apiRequest.post('/login', params);

// 导出获取所有角色信息的 GET 请求 API
export const GetAllRoleInfoAPI = (params) => apiRequest.get('/usr/role-info', { params });

// 导出获取角色可见性信息的 GET 请求 API
export const GetIfRoleSeenAPI = (params) => apiRequest.get('/orgRole/seen', { params });

// 导出获取班级成员列表的 GET 请求 API
export const GetMemberByKlassAPI = (params) => apiRequest.get(`/klass/${params.id}/member-list`, { params });

// 导出获取组织角色列表的 GET 请求 API
export const GetRoleByOrgAPI = (params, id) => apiRequest.get(`/org/${id}/role-list`, { params });

// 导出删除用户的 GET 请求 API
export const DeleteUserAPI = (params) => apiRequest.get(`/user/delete/${params.id}`, { params });

// 导出授予用户角色的 POST 请求 API
export const PostGrantUserRoleAPI = (params) => apiRequest.post('/user/grant', params);

// 导出获取用户角色的 GET 请求 API
export const GetRoleByUserAPI = (params) => apiRequest.get('/role/user', { params });

// 导出获取组织通知列表的 GET 请求 API
export const GetNoticeByGroupAPI = (params) => apiRequest.get('/notice', { params });

// 导出获取当前角色的 GET 请求 API
export const GetCurrentRolesAPI = (params) => apiRequest.get('/role', { params });

// 导出添加通知的 POST 请求 API
export const PostAddNoticeAPI = (params) => apiRequest.post('/notice/add', params);

// 导出获取班级范围的 GET 请求 API
export const GetRangeKlassAPI = (params) => apiRequest.get('/klass', { params });

// 导出获取当前用户信息的 GET 请求 API
export const GetCurrentUserAPI = (params) => apiRequest.get('/userInfo', { params });

// 导出提交回复的 POST 请求 API
export const PostReplyAPI = (params) => apiRequest.post('/reply/add', params);

// 导出提交补充信息的 POST 请求 API
export const PostSupplyAPI = (params) => apiRequest.post('/supply/add', params);

// 导出获取用户自有角色的 POST 请求 API
export const PostOwnRoleAPI = (params) => apiRequest.post('/role/own', params);

// 导出删除用户组织角色的 POST 请求 API
export const PostDeleteUserOrgRoleAPI = (params) => apiRequest.post('/userOrgRole/delete', params);

// 导出删除用户所有组织角色的 POST 请求 API
export const PostDeleteAllUserOrgRoleAPI = (params) => apiRequest.post('/userOrgRole/deleteAll', params);

// 导出编辑角色的 POST 请求 API
export const PostEditRoleAPI = (params) => apiRequest.post('/orgRole/edit', params);

// 导出添加角色的 GET 请求 API
export const GetAddRoleAPI = (params) => apiRequest.get('/orgRole/add', { params });

// 导出删除角色的 GET 请求 API
export const GetDeleteRoleAPI = (params) => apiRequest.get('/orgRole/delete', { params });

// 导出撤销用户权限的 GET 请求 API
export const GetRevokeAPI = (params) => apiRequest.get('/user/revoke', { params });

// 导出获取个人消息列表的 GET 请求 API
export const GetPersMsgAPI = (params) => apiRequest.get('/persMsg', { params });

// 导出确认消息的 GET 请求 API
export const GetConfirmMsgAPI = (params, id) => apiRequest.get(`/persMsg/${id}`, { params });

// 导出接受消息的 GET 请求 API
export const GetAcceptMsgAPI = (params, id) => apiRequest.get(`/persMsg/${id}/accept`, { params });

// 导出拒绝消息的 GET 请求 API
export const GetRejectMsgAPI = (params, id) => apiRequest.get(`/persMsg/${id}/reject`, { params });

// 导出根据用户名获取用户信息的 GET 请求 API
export const GetUserByNameAPI = (params) => apiRequest.get('/user', { params });

// 导出添加邀请消息的 GET 请求 API
export const GetAddInviteMsgAPI = (params, id, username) => apiRequest.get(`/persMsg/${id}/invite/${username}`, { params });

// 导出判断是否为超级角色的 GET 请求 API
export const GetIsSuperRoleAPI = (params) => apiRequest.get('/superRole', { params });

// 导出授予用户角色的 API 函数
export const GrantUserRoleAPI = (params) => {
    return apiRequest.post('/user/grant', params);
};

// 导出撤销用户角色的 API 函数
export const RevokeUserRoleAPI = (params) => apiRequest.get('/user/revoke', { params });

// 导出将用户角色设置为基础角色的 POST 请求 API
export const PostSetUserRoleToBasicAPI = (params) => apiRequest.post('/orgRole/setUserRoleToBasic', params);

// 导出添加具有基础角色用户的 POST 请求 API
export const PostAddUserWithBasicRoleAPI = (params) => apiRequest.post('/orgRole/addUserWithBasicRole', params);

// 数据验证与错误处理
const request = axios.create({
    baseURL: 'http://localhost:1025/api',
    timeout: 5000
});

// 请求拦截器
request.interceptors.request.use(config => {
    // 对请求参数进行验证
    if (config.method === 'get' && config.params) {
        for (let key in config.params) {
            if (config.params[key] === null || config.params[key] === undefined) {
                throw new Error(`参数 ${key} 不能为空`);
            }
        }
    }
    if (config.method === 'post' && config.data) {
        for (let key in config.data) {
            if (config.data[key] === null || config.data[key] === undefined) {
                throw new Error(`参数 ${key} 不能为空`);
            }
        }
    }
    return config;
}, error => {
    console.error('请求参数验证失败:', error);
    return Promise.reject(error);
});

// 响应拦截器
request.interceptors.response.use(response => {
    if (response.data.code !== 200) {
        throw new Error(response.data.message);
    }
    return response;
}, error => {
    console.error('请求出错:', error);
    return Promise.reject(error);
});

export default request;

// 分页和排序参数
export const GetGroupListAPI = (params) => request.get('/group', {
    params: {
        page: params.page || 1,
        pageSize: params.pageSize || 10,
        sortField: params.sortField || 'id',
        sortOrder: params.sortOrder || 'asc',
        ...params
    }
});

export const GetMemberByOrgAPI = (params, id) => request.get(`/org/${id}/member-list`, {
    params: {
        page: params.page || 1,
        pageSize: params.pageSize || 10,
        sortField: params.sortField || 'id',
        sortOrder: params.sortOrder || 'asc',
        ...params
    }
});