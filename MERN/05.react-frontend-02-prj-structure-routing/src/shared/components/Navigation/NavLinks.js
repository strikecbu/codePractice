import React, { useContext } from 'react';
import { NavLink } from 'react-router-dom';

import './NavLinks.css';
import { AuthContext } from '../../contexts/auth-context';

const NavLinks = (props) => {
  const authCtx = useContext(AuthContext);
  return (
    <ul className="nav-links">
      <li>
        <NavLink to="/" exact>
          ALL USERS
        </NavLink>
      </li>
      {authCtx.isLogin && (
        <li>
          <NavLink to="/u1/places">MY PLACES</NavLink>
        </li>
      )}
      {authCtx.isLogin && (
        <li>
          <NavLink to="/places/new">ADD PLACE</NavLink>
        </li>
      )}
      {!authCtx.isLogin && (
        <li>
          <NavLink to="/auth">AUTHENTICATE</NavLink>
        </li>
      )}
      {authCtx.isLogin && (
        <li>
          <button onClick={authCtx.logout}>LOGOUT</button>
        </li>
      )}
    </ul>
  );
};

export default NavLinks;
