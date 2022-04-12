import { FirebaseApp, initializeApp } from 'firebase/app';
import { getAuth, signInWithEmailAndPassword } from 'firebase/auth';
import { createContext, PropsWithChildren, useEffect, useState } from 'react';

export const AppContext = createContext<appContextType>({
  firebaseApp: undefined,
  isLoggedIn: false,
  error: '',
  onLogout: () => {},
  onLogin: (email, password) => {},
});

type appContextType = {
  firebaseApp: FirebaseApp | undefined;
  isLoggedIn: boolean;
  error: string;
  onLogout: () => void;
  onLogin: (email: string, password: string) => void;
};

// Set the configuration for your app
// TODO: Replace with your app's config object
const firebaseConfig = {
  apiKey: 'AIzaSyBckdILxJi6A04dC8h6BQoSSdWUXQXRevE',
  authDomain: 'localhost',
  // databaseURL: '<your-database-url>',
  storageBucket: 'gs://react-download.appspot.com',
};
function AppContextProvider(props: PropsWithChildren<{}>) {
  const [token, setToken] = useState<string>();
  const [error, setError] = useState<string>('');
  const [firebaseApp, setFirebaseApp] = useState<FirebaseApp>();
  const isLogin = !!token;

  useEffect(() => {
    const app = initializeApp(firebaseConfig);
    setFirebaseApp(app);
    console.log('set Firebase app');
  }, []);
  const onLogout = () => {};
  const onLogin = async (email: string, password: string) => {
    const auth = getAuth();
    try {
      const userCredential = await signInWithEmailAndPassword(
        auth,
        email,
        password
      );
      const respToken = await userCredential.user.getIdToken();
      setToken(respToken);
    } catch (err: any) {
      const s: string = err.message;
      setError(s.replaceAll('Firebase: ', ''));
    }
  };
  const initData: appContextType = {
    firebaseApp,
    isLoggedIn: isLogin,
    error,
    onLogin,
    onLogout,
  };
  return (
    <AppContext.Provider value={initData}>{props.children}</AppContext.Provider>
  );
}

export default AppContextProvider;
