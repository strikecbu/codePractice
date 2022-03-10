import { Fragment } from 'react';
import { useSelector } from 'react-redux';
import Counter from './components/Counter';
import Header from './components/Header';
import Auth from './components/Auth';
import UserProfile from './components/UserProfile';


function App() {

  const isAuthentiaction = useSelector(state => state.auth.isAuthentiaction)

  return (
    <Fragment>
      <Header />
      {!isAuthentiaction && <Auth />}
      {isAuthentiaction && <UserProfile />}
      <Counter />
    </Fragment>
  );
}

export default App;
