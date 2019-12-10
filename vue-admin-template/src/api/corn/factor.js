import request from '@/utils/request'


export function readList(params) {
  return request({
    url: '/corn/factor',
    method: 'get',
    params: params
  })
}

export function readOne(id) {
  return request({
    url: '/corn/factor/'+ id,
    method: 'get'
  })
}

// crud
export function create(data) {
  return request({
    url: '/corn/factor',
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
    url: '/corn/factor/'+data.id,
    method: 'delete'
  })
}
