import { createApp } from 'vue';
import { createStore } from 'vuex';

import App from './App.vue';

const store = createStore({
    state() {
        return {
            counter: 110,
            isLogin: false
        }
    },
    mutations: {
        increasementCounter(state) {
            console.log('increasement')
            state.counter = state.counter + 1;
        },
        increaseCounter(state, payload) {
            state.counter += payload.value
        },
        changeLoginStatus(state, payload) {
            state.isLogin = payload.value
        }
    },
    actions: {
        increaseAfter2second(context, payload) {
            setTimeout(()=> {
                context.commit('increaseCounter', payload)
            }, 2000)
        },
        login(context) {
            setTimeout(()=>{
                context.commit('changeLoginStatus', {value: true})
            }, 1500)
        },
        logout(context) {
            context.commit('changeLoginStatus', {value: false})
        }
    },
    getters: {
        getCounter(state) {
            return state.counter
        },
        getDoubleCounter(_, getters) {
            return getters.getCounter * 2
        },
        getLoginStatus(state) {
            return state.isLogin
        }
    }
})

const app = createApp(App);
app.use(store);
app.mount('#app');
