import Cookies from 'js-cookie'

// const TokenKey = 'vue_admin_template_token'
const TokenKey = 'AUTH_TOKEN'

export function getToken() {
  console.log("getToken",Cookies.get(TokenKey))
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  console.log("setToken",token)
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
