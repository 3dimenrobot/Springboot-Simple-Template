import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    books: []
  },
  mutations: {
    // 所有mutations中的方法的第一个参数一定是state变量，用来进行对state中的状态的操作
    // 第二个参数是可选参数，用于调用该 mutations 方法的时候传参
    initBooks (state, books) {
      state.books = books
    },
    addNewBook (state, book) {
      state.books.unshift(book)
    }
  },
  actions: {
    // { commit }是参数解构的写法，详见ES6语法
    fetchData ({commit}) {
      axios.get('http://127.0.0.1:8081/api/books')
        .then(function (response) {
          commit('initBooks', response.data)
        })
        .catch(function (error) {
          console.log(error)
        })
    },
    // book是调用该操作时传过来的附加参数
    addItem ({commit}, book) {
      return axios.post('http://127.0.0.1:8081/api/add', book)
        .then(function (response) {
          if (!response || response.status !== 200 || response.data.err) {
            return true
          } else {
            commit('addNewBook', book)
            return false
          }
        })
    }
  },
  getters: {
    doneBooks: state => {
      return state.books.filter(book => book.done)
    }
  }
})

export default store
