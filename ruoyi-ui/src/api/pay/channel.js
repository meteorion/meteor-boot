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
// 新增渠道
export function addPayChannel(params) {
  return request({
    url: '/pay/channel/create',
    method: 'post',
    data: params
  })
}
// 修改渠道
export function updatePayChannel(params) {
  return request({
    url: '/pay/channel/update',
    method: 'put',
    data: params
  })
}

// 新增支付配置
export function addPayChannelConfig(params) {
  return request({
    url: '/pay/channel/config/add',
    method: 'post',
    data: params
  })
}

// 修改支付配置
export function updatePayChannelConfig(params) {
  return request({
    url: '/pay/channel/config/update',
    method: 'put',
    data: params
  })
}
