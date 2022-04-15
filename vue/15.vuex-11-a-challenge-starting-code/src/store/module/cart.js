export default {
    namespaced: true,
    state() {
        return {
            items: [],
            total: 0,
            qty: 0
        }
    },
    mutations: {
        addItem(state, {product}) {
            state.items = [...state.items, product]
        },
        removeItem(state, {productId}) {
            state.items = state.items.filter(item => {
                console.log(item.productId, productId)
                return item.productId !== productId
            })
            console.log(state.items)
        },
        increaseItemQty(state, {productId}) {
            const index = state.items.findIndex(item => item.productId === productId)
            state.items[index].qty++;
        },
        decreaseItemQty(state, {productId}) {
            const index = state.items.findIndex(item => item.productId === productId)
            state.items[index].qty--;
        },
        increaseQty(state, {value}) {
            state.qty += value;
        },
        decreaseQty(state, {value}) {
            state.qty -= value;
        },
        addPriceToTotal(state, {price}) {
            state.total += price;
        },
        removePriceFromTotal(state, {price}) {
            state.total -= price
        }
    },
    actions: {
        addProductToCart(context, {productData}) {
            const productInCartIndex = context.state.items.findIndex(
                (ci) => ci.productId === productData.id
            );

            if (productInCartIndex >= 0) {
                context.commit('increaseItemQty', {productId: productData.id})
            } else {
                const newItem = {
                    productId: productData.id,
                    title: productData.title,
                    image: productData.image,
                    price: productData.price,
                    qty: 1,
                };
                context.commit("addItem", {product: newItem})
            }
            context.commit('increaseQty', {value: 1})
            context.commit('addPriceToTotal', {price: productData.price})
        },

        removeProductFromCart(context, {prodId}) {
            const productInCartIndex = context.state.items.findIndex(
                (cartItem) => cartItem.productId === prodId
            );

            const prodData = context.state.items[productInCartIndex];

            context.commit("decreaseQty", {value: prodData.qty})
            context.commit('removePriceFromTotal', {price: prodData.price * prodData.qty})
            context.commit("removeItem", {productId: prodId})
        },
    },
    getters: {
        totalQuantity(state) {
            return state.qty
        },
        totalPrice(state) {
            return state.total
        },
        allProductInCart(state) {
            return state.items
        }
    }
}
