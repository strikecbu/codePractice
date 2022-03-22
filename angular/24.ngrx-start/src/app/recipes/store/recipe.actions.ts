import { Action } from "@ngrx/store";
import { Recipe } from "../recipe.model";

export const SET_RECIPES = "[Recipe] set recipes";
export const UPDATE_RECIPE = "[Recipe] UPDATE_RECIPE";
export const ADD_RECIPE = "[Recipe] ADD_RECIPE";
export const DELETE_RECIPE = "[Recipe] DELETE_RECIPE";

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
  constructor(public payload: number) {
  }
}

export type RecipeActions = SetRecipes | UpdateRecipe | AddRecipe | DeleteRecipe;
