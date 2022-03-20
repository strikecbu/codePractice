import { ActionReducerMap } from "@ngrx/store/src/models";

import * as fromShoppingList from "../shopping-list/store/shopping-list.reducer";
import * as fromAuth from "../auth/store/auth.reducer";

export interface AppState {
  shoppingList: fromShoppingList.State;
  auth: fromAuth.State
}

export const appReducers: ActionReducerMap<AppState> = {
  shoppingList: fromShoppingList.shoppingListReducer,
  auth: fromAuth.authReducer
};

