import request from '@/utils/request'

export function readList(params) {
  return request({
    url: '/corn/newsItem',
    method: 'get',
    params: params
  })
}

export function create(data) {
  return request({
    url: '/corn/newsItem',
    method: 'post',
    data: data
  })
}

export function remove(data) {
  return request({
    url: '/corn/newsItem/'+data.id,
    method: 'delete'
  })
}
