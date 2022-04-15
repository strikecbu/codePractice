export default {
    namespaced: true,
    state() {
        return {
            products: []
        }
    },
    mutations: {
        setProducts(state, payload) {
            state.products = payload.products;
        }
    },
    actions: {
        loadProducts(context, payload) {
            context.commit('setProducts', payload)
        }
    },
    getters: {
        getAllProducts(state) {
            return state.products
        }
    }
}
