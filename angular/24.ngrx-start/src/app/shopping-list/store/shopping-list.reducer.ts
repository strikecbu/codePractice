import { Ingredient } from "../../shared/ingredient.model";
import * as ShpListActions from "./shopping-list.actions";

export type ShoppingListStateType = {
  ingredients: Ingredient[];
};

const initState: ShoppingListStateType = {
  ingredients: [new Ingredient("Apples", 5), new Ingredient("Tomatoes", 10)],
};

export function shoppingListReducer(
  state = initState,
  action: ShpListActions.ShoppingListActions
): ShoppingListStateType {
  switch (action.type) {
    case ShpListActions.ADD_INGREDIENT:
      return {
        ...state,
        ingredients: [...state.ingredients, action.payload],
      };
    case ShpListActions.ADD_INGREDIENTS:
      return {
        ...state,
        ingredients: [...state.ingredients, ...action.payload],
      };
    default:
      return state;
  }
}
