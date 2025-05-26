import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listAskapplystatus(query) {
  return request({
    url: '/system/askapplystatus/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getAskapplystatus(askNo) {
  return request({
    url: '/system/askapplystatus/' + askNo,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addAskapplystatus(data) {
  return request({
    url: '/system/askapplystatus',
    method: 'post',
    data: data
  })
}
export function assignUserBatch(ids, assignUserId) {
  return request({
    url: '/system/askapplystatus/batchUpdateUser',
    method: 'post',
    data: {
      askno: ids,
      userid: assignUserId
    }
  })
}

// 修改【请填写功能名称】
export function updateAskapplystatus(data) {
  return request({
    url: '/system/askapplystatus',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delAskapplystatus(askNo) {
  return request({
    url: '/system/askapplystatus/' + askNo,
    method: 'delete'
  })
}
export function importTemplate() {
  return request({
    url: '/system/askapplystatus/importTemplate',
    method: 'get',
    responseType: 'blob'   // 关键，告诉 axios 返回二进制流
  })
}
