import { Action } from "@ngrx/store";
import { Recipe } from "../recipe.model";

export const SET_RECIPES = "[Recipe] set recipes";
export const UPDATE_RECIPE = "[Recipe] UPDATE_RECIPE";
export const ADD_RECIPE = "[Recipe] ADD_RECIPE";
export const DELETE_RECIPE = "[Recipe] DELETE_RECIPE";
export const START_STORE_RECIPES = "[Recipe] START_STORE_RECIPES";
export const START_FETCH_RECIPES = "[Recipe] START_FETCH_RECIPES";



export class SetRecipes implements Action {
  readonly type = SET_RECIPES;

  constructor(public payload: Recipe[]) {}
}

export class UpdateRecipe implements Action {
  readonly type = UPDATE_RECIPE;

  constructor(public payload: { index: number; recipe: Recipe }) {}
}

export class AddRecipe implements Action {
  readonly type = ADD_RECIPE;

  constructor(public payload: Recipe) {}
}

export class DeleteRecipe implements Action {
  readonly type = DELETE_RECIPE;

  constructor(public payload: number) {}
}

export class StartStoreRecipes implements Action {
  readonly type = START_STORE_RECIPES;
}
export class StartFetchRecipes implements Action {
  readonly type = START_FETCH_RECIPES;
}

export type RecipeActions =
  | SetRecipes
  | UpdateRecipe
  | AddRecipe
  | DeleteRecipe
  | StartStoreRecipes;
