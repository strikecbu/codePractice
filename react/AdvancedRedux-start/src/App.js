import Cart from './components/Cart/Cart';
import Layout from './components/Layout/Layout';
import Products from './components/Shop/Products';
import { useSelector, useDispatch } from 'react-redux';
import { Fragment, useEffect } from 'react';
import { sendCartData, fetchCartData } from './store/cart-slice'
import Notification from './components/UI/Notification';
let isInitial = true;

function App() {

  const dispatch = useDispatch();
  const isShowCart = useSelector(state => state.ui.isShowCart);
  const cart = useSelector(state => state.cart);
  const notification = useSelector(state => state.ui.notification);

  useEffect(()=> {
    dispatch(fetchCartData());
  }, [dispatch]);

  useEffect(() => {
    if(isInitial) {
      isInitial = false;
      return;
    }
    if(cart.change) {
      dispatch(sendCartData(cart));
    }
    
  }, [cart, dispatch])

  console.log('App init')
  return (
    <Fragment>
      {notification && <Notification status={notification.status} title={notification.title} message={notification.message} />}
      <Layout>
        {isShowCart && <Cart />}
        <Products />
      </Layout>
    </Fragment>
  );
}

export default App;
