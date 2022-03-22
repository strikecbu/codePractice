import { Injectable } from "@angular/core";
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { Store } from "@ngrx/store";
import { map, switchMap, take } from "rxjs/operators";
import { of } from "rxjs";

import { Recipe } from "./recipe.model";
import { DataStorageService } from "../shared/data-storage.service";
import { RecipeService } from "./recipe.service";
import { AppState } from "../store/app.reducer";
import { StartFetchRecipes } from "./store/recipe.actions";

@Injectable({ providedIn: "root" })
export class RecipesResolverService implements Resolve<Recipe[]> {
  constructor(
    private dataStorageService: DataStorageService,
    private recipesService: RecipeService,
    private store: Store<AppState>
  ) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    // const recipes = this.recipesService.getRecipes();

    return this.store.select("recipe").pipe(
      take(1),
      map((state) => state.recipes),
      switchMap((recipes) => {
        if (recipes.length === 0) {
          this.store.dispatch(new StartFetchRecipes());
        }
        // return of(recipes);
        // return this.dataStorageService.fetchRecipes();
        return of(recipes);
      })
    );

    // if (recipes.length === 0) {
    //   return this.dataStorageService.fetchRecipes();
    // } else {
    //   return recipes;
    // }
  }
}
