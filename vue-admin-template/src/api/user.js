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

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function getList(params) {
  return request({
    url: '/rbac/user',
    method: 'get',
    params: params
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
