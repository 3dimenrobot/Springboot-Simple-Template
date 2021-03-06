import request from '@/utils/request'
import Qs from 'qs'

export function login(data) {
  return request({
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    url: '/user/login',
    method: 'post',
    data: Qs.stringify(data)
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function readList(params) {
  return request({
    url: '/rbac/user',
    method: 'get',
    params: params
  })
}

// 查询权限和用户信息
export function getInfo(token) {
  return request({
    url: '/rbac/user/info',
    method: 'get',
    params: { token }
  })
}



// crud
export function create(data) {
  return request({
    url: '/rbac/user',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/rbac/user/'+data.id,
    method: 'put',
    data: data
  })
}

export function remove(data) {
  return request({
    url: '/rbac/user/'+data.id,
    method: 'delete'
  })
}
