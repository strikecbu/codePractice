import { createStore } from "vuex";
import counterModule from "./counter-module";


const store = createStore({
    modules: {
      counter: counterModule,
    },
    state() {
      return {
        isLogin: false,
      };
    },
    mutations: {
      changeLoginStatus(state, payload) {
        state.isLogin = payload.value;
      },
    },
    actions: {
      login(context) {
        setTimeout(() => {
          context.commit('changeLoginStatus', { value: true });
        }, 1500);
      },
      logout(context) {
        context.commit('changeLoginStatus', { value: false });
      },
    },
    getters: {
      getLoginStatus(state) {
        return state.isLogin;
      },
    },
  });

  export default store;