import { Ingredient } from "../../shared/ingredient.model";
import * as ShpListActions from "./shopping-list.actions";

type stateType = {
  ingredients: Ingredient[];
};

const initState: stateType = {
  ingredients: [new Ingredient("Apples", 5), new Ingredient("Tomatoes", 10)],
};

export function shoppingListReducer(
  state = initState,
  action: ShpListActions.AddIngredient
): stateType {
  switch (action.type) {
    case ShpListActions.ADD_INGREDIENT:
      return {
        ...state,
        ingredients: [...state.ingredients, action.payload],
      };
  }
}
