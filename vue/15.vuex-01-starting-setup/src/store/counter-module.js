export default {
    state() {
      return {
        counter: 110,
      };
    },
    mutations: {
      increasementCounter(state) {
        console.log('increasement');
        state.counter = state.counter + 1;
      },
      increaseCounter(state, payload) {
        state.counter += payload.value;
      },
    },
    actions: {
      increaseAfter2second(context, payload) {
        setTimeout(() => {
          context.commit('increaseCounter', payload);
        }, 2000);
      },
    },
    getters: {
      getCounter(state) {
        return state.counter;
      },
      getDoubleCounter(_, getters) {
        return getters.getCounter * 2;
      },
    },
  };