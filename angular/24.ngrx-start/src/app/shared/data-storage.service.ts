import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { map, take, tap } from "rxjs/operators";

import { Recipe } from "../recipes/recipe.model";
import { RecipeService } from "../recipes/recipe.service";
import { AuthService } from "../auth/auth.service";
import { Store } from "@ngrx/store";
import { AppState } from "../store/app.reducer";
import * as RecipeActions from "../recipes/store/recipe.actions"

@Injectable({ providedIn: "root" })
export class DataStorageService {
  constructor(
    private http: HttpClient,
    private recipeService: RecipeService,
    private authService: AuthService,
    private store: Store<AppState>
  ) {}

  storeRecipes() {
    let recipes;
    this.store
      .select("recipe")
      .pipe(
        take(1),
        map((state) => state.recipes)
      )
      .subscribe((recs) => {
        recipes = recs;
      });
    this.http
      .put(
        "https://angular-http2-3d5ab-default-rtdb.asia-southeast1.firebasedatabase.app/recipes.json",
        recipes
      )
      .subscribe((response) => {
        console.log(response);
      });
  }

  fetchRecipes() {
    return this.http
      .get<Recipe[]>(
        "https://angular-http2-3d5ab-default-rtdb.asia-southeast1.firebasedatabase.app/recipes.json"
      )
      .pipe(
        map((recipes) => {
          return recipes.map((recipe) => {
            return {
              ...recipe,
              ingredients: recipe.ingredients ? recipe.ingredients : [],
            };
          });
        }),
        tap((recipes) => {
          this.store.dispatch(new RecipeActions.SetRecipes(recipes));
          // this.recipeService.setRecipes(recipes);
        })
      );
  }
}
