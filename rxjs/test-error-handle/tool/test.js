import {
  fromEvent,
  tap,
  switchMap,
  catchError,
  mergeMap,
  map,
  of,
  flatMap,
  from,
} from "rxjs";

export const bind = () => {
  const click$ = fromEvent(document.getElementById("btn"), "click").pipe(
    map(() => "Data")
  );

  click$
    .pipe(
      switchMap((data) => {
        console.log(data);
        return of(data).pipe(
          switchMap(sideEffect),
          catchError((e) => {
            return of(e);
          })
        );
      }),
      map((data) => {
        if (data instanceof Error) {
          return "Not work! " + data.message;
        }
        return data;
      }),

      tap(console.log)
    )
    .subscribe();
};

const sideEffect = (d) => {
  return new Promise((res, rej) => {
    setTimeout(() => {
        // res("work");
    //   rej(`oops! ${d} is not work`);
      rej(new Error(`oops! ${d} is not work`));
    }, 200);
  });
};
