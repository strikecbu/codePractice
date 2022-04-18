import {createStore} from "vuex";
import authModule from './modules/auth'
import cartModule from './modules/cart'
import productModule from './modules/product'

const store = createStore({
    modules: {
        auth: authModule,
        cart: cartModule,
        product: productModule
    }
})
export default store;
