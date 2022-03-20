import { Component, OnInit } from '@angular/core';
import {Observable} from 'rxjs';
import {Store} from "@ngrx/store";
import {map} from "rxjs/operators";

import { Ingredient } from '../shared/ingredient.model';
import { LoggingService } from '../logging.service';
import * as ShoppingListActions from "./store/shopping-list.actions";
import * as fromApp from "../store/app.reducer";

@Component({
  selector: "app-shopping-list",
  templateUrl: "./shopping-list.component.html",
  styleUrls: ["./shopping-list.component.css"],
})
export class ShoppingListComponent implements OnInit {
  ingredients: Observable<Ingredient[]>;

  constructor(
    private loggingService: LoggingService,
    private store: Store<fromApp.AppState>
  ) {}

  ngOnInit() {
    this.ingredients = this.store.select("shoppingList").pipe(
      map((list) => {
        return list.ingredients;
      })
    );

    this.loggingService.printLog("Hello from ShoppingListComponent ngOnInit!");
  }

  onEditItem(index: number) {
    this.store.dispatch(new ShoppingListActions.StartEdit(index))
  }

}
