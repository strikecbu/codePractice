import { Ingredient } from "../../shared/ingredient.model";
import * as ShpListActions from "./shopping-list.actions";
import { STOP_EDIT } from "./shopping-list.actions";

export interface State {
  ingredients: Ingredient[];
  editedItemIndex: number;
  editedItem: Ingredient;
}

const initState: State = {
  ingredients: [new Ingredient("Apples", 5), new Ingredient("Tomatoes", 10)],
  editedItemIndex: -1,
  editedItem: null,
};

export function shoppingListReducer(
  state = initState,
  action: ShpListActions.ShoppingListActions
): State {
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
    case ShpListActions.UPDATE_INGREDIENT:
      const updateIngredient = {
        ...state.ingredients[state.editedItemIndex],
        ...action.payload,
      };
      const updateIngredients = [...state.ingredients];
      updateIngredients[state.editedItemIndex] = updateIngredient;

      return {
        ...state,
        ingredients: updateIngredients,
        editedItem: null,
        editedItemIndex: -1,
      };
    case ShpListActions.DELETE_INGREDIENT:
      return {
        ...state,
        ingredients: state.ingredients.filter((ing, ingIndex) => {
          return ingIndex !== state.editedItemIndex;
        }),
        editedItem: null,
        editedItemIndex: -1,
      };
    case ShpListActions.START_EDIT:
      return {
        ...state,
        editedItemIndex: action.payload,
        editedItem: { ...state.ingredients[action.payload] },
      };
    case STOP_EDIT:
      return {
        ...state,
        editedItem: null,
        editedItemIndex: -1,
      };
    default:
      return state;
  }
}
