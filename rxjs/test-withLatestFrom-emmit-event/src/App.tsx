import { FormEvent, useCallback, useEffect } from 'react';
import {
  BehaviorSubject,
  fromEvent,
  map,
  skip,
  tap,
  withLatestFrom,
} from 'rxjs';
import './App.css';
import logo from './logo.svg';

interface Employee {
  id: number;
  name: string;
  age: number;
}
const store$ = new BehaviorSubject<Employee[]>([]);

let count = 0;
function App() {
  const updateInput = async () => {
    console.log('update now');
    const data = await getData();
    console.log(`get data back:`, data);
    store$.next(data);
  };

  useEffect(() => {
    const subs = fromEvent(
      document.getElementById('textInput') as any,
      'change'
    )
      .pipe(
        withLatestFrom(store$),
        tap(([ele, employees]) => {
          // console.log(((ele as Event).target as HTMLInputElement).value);
          console.log('withLatestFrom', employees);
        })
      )
      .subscribe();

    const sub0 = store$
      .pipe(
        skip(1),
        map((array) => {
          return array.sort((d1, d2) => d2.age - d1.age);
        }),
        map((array) => array[0]),
        tap((data) => {
          console.log(`set input value:`, data);
          const textInput = document.getElementById(
            'textInput'
          ) as HTMLInputElement;
          textInput.value = data.name;
          textInput.dispatchEvent(new Event('change'));
          // setTimeout(() => {
          //   textInput.dispatchEvent(new Event('change'));
          // }, 0);
        })
      )
      .subscribe();

    const subs1 = fromEvent(document.getElementById('storeBtn') as any, 'click')
      .pipe(
        withLatestFrom(store$),
        tap(([_, values]) => {
          console.log(values);
        })
      )
      .subscribe();

    console.log('execute it');
    return () => {
      console.log('leave it');
      subs.unsubscribe();
      subs1.unsubscribe();
      sub0.unsubscribe();
    };
  }, []);

  const getData = useCallback(async () => {
    return new Promise<Employee[]>((res, reject) => {
      setTimeout(() => {
        res([
          {
            id: count,
            name: 'Andy',
            age: 18,
          },
          {
            id: count,
            name: 'Tom',
            age: 16,
          },
        ]);
        count++;
      }, 800);
    });
  }, []);

  const cancelSubmit = (evt: FormEvent) => {
    evt.preventDefault();
  };

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
        <form
          onSubmit={cancelSubmit}
          style={{ display: 'flex', flexDirection: 'column', gap: '5px' }}
        >
          <button onClick={updateInput}>update</button>
          <button id="storeBtn">getStore</button>
          <input id="textInput" type="text" />
        </form>
      </header>
    </div>
  );
}

export default App;
