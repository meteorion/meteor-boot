import request from '@/utils/request'

// 查询通道列表
export function listPayChannel(query) {
  return request({
    url: '/pay/channel/list',
    method: 'get',
    params: query
  })
}
// 查询通道详情
export function getPayChannel(query) {
  return request({
    url: '/pay/channel/get',
    method: 'get',
    params: query
  })
}

