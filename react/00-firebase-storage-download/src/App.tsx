import {FormEvent, useContext, useRef} from 'react';
import './App.css';
import Download from './components/Download';
import iisiLogo from './logo_iisi.png';

import {AppContext} from './store/AppContext';


function App() {
    const ctx = useContext(AppContext);
    const accountRef = useRef<HTMLInputElement>(null);
    const passwordRef = useRef<HTMLInputElement>(null);

    const onSubmitHandler = ($event: FormEvent) => {
        $event.preventDefault();
        const accout = accountRef.current?.value ?? '';
        const passord = passwordRef.current?.value ?? '';
        ctx.onLogin(accout, passord);
    };

    return (
        <div className="App">
            <header className="App-header">
                {/* <img src={logo} className="App-logo" alt="logo" /> */}
                <img src={iisiLogo} className="" alt="iisiLogo"/>
                {ctx.isLoggedIn && <Download word="IISI unzip tool"></Download>}
                {!ctx.isLoggedIn && (
                    <form>
                        <div className='login-form'>
                            <div className='form-control'>
                                <label>Account:</label>
                                <input type="text" ref={accountRef}/>
                            </div>
                            <div className='form-control'>
                                <label>Password:</label>
                                <input id="password" type="password" ref={passwordRef}/>
                            </div>
                            <div>
                                <button onClick={onSubmitHandler}>Login</button>
                            </div>
                            <div style={{color: 'red'}}>{ctx.error}</div>
                        </div>
                    </form>
                )}
            </header>
        </div>
    );
}

export default App;
