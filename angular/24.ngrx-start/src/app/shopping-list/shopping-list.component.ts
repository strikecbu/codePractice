import { Component, OnInit, OnDestroy } from '@angular/core';
import {Observable, Subscription} from 'rxjs';
import {Store} from "@ngrx/store";
import {map} from "rxjs/operators";

import { Ingredient } from '../shared/ingredient.model';
import { ShoppingListService } from './shopping-list.service';
import { LoggingService } from '../logging.service';
import {ShoppingListStateType} from "./store/shopping-list.reducer";

@Component({
  selector: "app-shopping-list",
  templateUrl: "./shopping-list.component.html",
  styleUrls: ["./shopping-list.component.css"],
})
export class ShoppingListComponent implements OnInit, OnDestroy {
  ingredients: Observable<Ingredient[]>;
  // private subscription: Subscription;

  constructor(
    private slService: ShoppingListService,
    private loggingService: LoggingService,
    private store: Store<{ shoppingList: ShoppingListStateType }>
  ) {}

  ngOnInit() {
    // this.ingredients = this.slService.getIngredients();
    this.ingredients = this.store.select("shoppingList").pipe(
      map((list) => {
        return list.ingredients;
      })
    );
    // this.subscription = this.slService.ingredientsChanged.subscribe(
    //   (ingredients: Ingredient[]) => {
    //     this.ingredients = ingredients;
    //   }
    // );

    this.loggingService.printLog("Hello from ShoppingListComponent ngOnInit!");
  }

  onEditItem(index: number) {
    this.slService.startedEditing.next(index);
  }

  ngOnDestroy() {
    // this.subscription.unsubscribe();
  }
}
