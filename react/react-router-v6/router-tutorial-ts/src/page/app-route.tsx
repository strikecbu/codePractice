import { BrowserRouter, Route, Routes } from "react-router-dom";
import App from "../App";
import Invoice from "../invoice/invoice";
import Expenses from "./expenses";
import Invoices from "./invoices";
import React, { useContext } from "react";
import Auth from "../auth/auth";
import { AuthContext } from "../store/auth-context";

const AppRoute: React.FC = (props) => {
  const authCtx = useContext(AuthContext);
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />}>
          {authCtx.isAuthentication && (
            <Route path="invoices" element={<Invoices />}>
              <Route path=":invoiceId" element={<Invoice />} />
            </Route>
          )}
          <Route path="expenses" element={<Expenses />} />
          <Route path="auth/:mode" element={<Auth />} />
        </Route>
        <Route
          path="*"
          element={
            <h1 style={{ textAlign: "center" }}>There is nothing here...</h1>
          }
        />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRoute;
