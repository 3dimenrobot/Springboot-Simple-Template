import request from '@/utils/request'

export function readList(params) {
  return request({
    url: '/corn/presentSituation',
    method: 'get',
    params: params
  })
}

export function create(data) {
  return request({
    url: '/corn/presentSituation',
    method: 'post',
    data: data
  })
}

export function remove(data) {
  return request({
    url: '/corn/presentSituation/'+data.id,
    method: 'delete'
  })
}
