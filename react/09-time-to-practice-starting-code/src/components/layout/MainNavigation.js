import { NavLink } from "react-router-dom";
import { Fragment } from "react";
import classes from "./MainNavigation.module.css"

const MainNavigation = () => {
    return (
        <Fragment>
            <header className={classes.header}>
                <div className={classes.logo}>Greate Quote</div>
                <nav className={classes.nav}>
                    <ul>
                        <li>
                            <NavLink to='/quotes' activeClassName={classes.active}>All Quotes</NavLink>
                        </li>
                        <li>
                            <NavLink to='/new-quote' activeClassName={classes.active}>New Quote</NavLink>
                        </li>
                    </ul>
                </nav>
            </header>
        </Fragment>
    )
}

export default MainNavigation;