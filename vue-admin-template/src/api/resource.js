import request from '@/utils/request'


export function readList(params) {
  return request({
    url: '/rbac/resource',
    method: 'get',
    params: params
  })
}

// crud
export function create(data) {
  return request({
    url: '/rbac/resource',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/rbac/resource/'+data.id,
    method: 'put',
    data: data
  })
}

export function remove(data) {
  return request({
    url: '/rbac/resource/'+data.id,
    method: 'delete'
  })
}
