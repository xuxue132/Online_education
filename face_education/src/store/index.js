import Vue from "vue"
import Vuex from "vuex"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : '',
    tip: 100,
    show: {

    },
  },
  mutations: {
    init(){
      this.state.show = {

      }
      localStorage.removeItem('Authorization')
    },
    setToken(state, payload) {
      state.Authorization = payload.Authorization;
      localStorage.setItem('Authorization', payload.Authorization);

    },

  },
  actions: {},
  getters: {},
})