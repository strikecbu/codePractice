import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {Store} from "@ngrx/store";
import {catchError, map, switchMap, take, withLatestFrom} from "rxjs/operators";
import {HttpClient} from "@angular/common/http";
import {of} from "rxjs";

import * as RecipeActions from "./recipe.actions";
import {AppState} from "../../store/app.reducer";
import {Recipe} from "../recipe.model";

@Injectable()
export class RecipeEffects {
  storeRecipes$ = createEffect(
    () => {
      return this.actions$.pipe(
        ofType(RecipeActions.START_STORE_RECIPES),
        withLatestFrom(this.store.select("recipe")),
        switchMap(([action, recipeState]) => {
          return this.http.put(
            "https://angular-http2-3d5ab-default-rtdb.asia-southeast1.firebasedatabase.app/recipes.json",
            recipeState.recipes
          );
        }),
        // switchMap(() => {
        //   return this.store.select("recipe").pipe(
        //     take(1),
        //     map((state) => state.recipes),
        //     switchMap((recipes) => {
        //       return this.http.put(
        //         "https://angular-http2-3d5ab-default-rtdb.asia-southeast1.firebasedatabase.app/recipes.json",
        //         recipes
        //       );
        //     })
        //   );
        // })
      );
    },
    {dispatch: false}
  );

  fetchRecipes$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(RecipeActions.START_FETCH_RECIPES),
      switchMap(() => {
        return this.http
          .get<Recipe[]>(
            "https://angular-http2-3d5ab-default-rtdb.asia-southeast1.firebasedatabase.app/recipes.json"
          )
          .pipe(
            take(1),
            map((recipes) => {
              return recipes.map((recipe) => {
                return {
                  ...recipe,
                  ingredients: recipe.ingredients ? recipe.ingredients : [],
                };
              });
            }),
            map((recipes) => {
              return new RecipeActions.SetRecipes(recipes);
            }),
            catchError((error) => {
              return of({type: "NOT_EXIST"});
            })
          );
      })
    );
  });

  constructor(
    private actions$: Actions,
    private http: HttpClient,
    private store: Store<AppState>
  ) {
  }
}
