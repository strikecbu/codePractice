import ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";

import "./index.css";
import App from "./App";
import { AuthProvider } from "./store/auth-context";
import { Provider } from "react-redux";
import store from "./store/index";

ReactDOM.render(
  <BrowserRouter>
  <Provider store={store}>
    {/* <AuthProvider> */}
      <App />
    {/* </AuthProvider> */}
  </Provider>
  </BrowserRouter>,
  document.getElementById("root")
);
