import request from '@/utils/request'
import Qs from 'qs'


export function readList(params) {
  return request({
    url: '/rbac/role',
    method: 'get',
    params: params
  })
}
