import { createApp } from 'vue';
import { createStore } from 'vuex';

import App from './App.vue';

const store = createStore({
    state() {
        return {
            counter: 110
        }
    },
    mutations: {
        increasementCounter(state) {
            console.log('increasement')
            state.counter = state.counter + 1;
        },
        increaseCounter(state, payload) {
            state.counter += payload.value
        }
    },
    actions: {
        increaseAfter2second(state, payload) {
            setTimeout(()=> {
                state.commit('increaseCounter', payload)
            }, 2000)
        }
    },
    getters: {
        getCounter(state) {
            return state.counter
        },
        getDoubleCounter(_, getters) {
            return getters.getCounter * 2
        }
    }
})

const app = createApp(App);
app.use(store);
app.mount('#app');
