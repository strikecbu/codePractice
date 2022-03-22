import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { Store } from "@ngrx/store";
import { take } from "rxjs/operators";

import { Recipe } from "./recipe.model";
import { DataStorageService } from "../shared/data-storage.service";
import { RecipeService } from "./recipe.service";
import { AppState } from "../store/app.reducer";
import { SET_RECIPES, StartFetchRecipes } from "./store/recipe.actions";
import { Actions, ofType } from "@ngrx/effects";

@Injectable({ providedIn: "root" })
export class RecipesResolverService implements Resolve<Recipe[]> {
  constructor(
    private dataStorageService: DataStorageService,
    private recipesService: RecipeService,
    private store: Store<AppState>,
    private actions$: Actions
  ) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    // const recipes = this.recipesService.getRecipes();

    // return this.store.select("recipe").pipe(
    //   take(1),
    //   map((state) => state.recipes),
    //   switchMap((recipes) => {
    //     if (recipes.length === 0) {
    //       this.store.dispatch(new StartFetchRecipes());
    //     }
    //     // return of(recipes);
    //     // return this.dataStorageService.fetchRecipes();
    //     return of(recipes);
    //   })
    // );

    this.store.dispatch(new StartFetchRecipes());
    return this.actions$.pipe(ofType(SET_RECIPES), take(1));

    // if (recipes.length === 0) {
    //   return this.dataStorageService.fetchRecipes();
    // } else {
    //   return recipes;
    // }
  }
}
