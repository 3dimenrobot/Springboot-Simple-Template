import request from '@/utils/request'


export function readList(params) {
  return request({
    url: '/rbac/role',
    method: 'get',
    params: params
  })
}

export function readOne(id) {
  return request({
    url: '/rbac/role/'+ id,
    method: 'get'
  })
}

// crud
export function create(data) {
  return request({
    url: '/rbac/role',
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: '/rbac/role/'+data.id,
    method: 'put',
    data: data
  })
}

export function remove(data) {
  return request({
    url: '/rbac/role/'+data.id,
    method: 'delete'
  })
}
