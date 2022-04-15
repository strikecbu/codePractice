import {createStore} from "vuex";
import authModule from './module/auth'
import cartModule from './module/cart'
import productModule from './module/product'

const store = createStore({
    modules: {
        auth: authModule,
        cart: cartModule,
        product: productModule
    }
})
export default store;
