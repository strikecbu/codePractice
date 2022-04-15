export default {
    namespaced: true,
    state() {
        return {
            isLoggedIn: false,
        }
    },
    mutations: {
        setAuth(state, {isAuth}) {
            state.isLoggedIn = isAuth;
        }
    },
    actions: {
        login(context) {
            context.commit('setAuth', {isAuth: true})
        },
        logout(context) {
            context.commit('setAuth', {isAuth: false})
        }
    },
    getters: {
        isAuthentication(state) {
            return state.isLoggedIn
        }
    }
}
