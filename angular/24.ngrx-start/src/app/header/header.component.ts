import { Component, OnDestroy, OnInit } from "@angular/core";
import { Subscription } from "rxjs";
import { Store } from "@ngrx/store";
import { map } from "rxjs/operators";

import * as fromApp from "../store/app.reducer";
import { DataStorageService } from "../shared/data-storage.service";
import {Logout} from "../auth/store/auth.actions";
import {StartFetchRecipes, StartStoreRecipes} from "../recipes/store/recipe.actions";


@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
})
export class HeaderComponent implements OnInit, OnDestroy {
  isAuthenticated = false;
  private userSub: Subscription;

  constructor(
    private dataStorageService: DataStorageService,
    private store: Store<fromApp.AppState>
  ) {}

  ngOnInit() {
    this.userSub = this.store
      .select("auth")
      .pipe(map((authState) => authState.user))
      .subscribe((user) => {
        this.isAuthenticated = !!user;
        console.log(!user);
        console.log(!!user);
      });
  }

  onSaveData() {
    // this.dataStorageService.storeRecipes();
    this.store.dispatch(new StartStoreRecipes());
  }

  onFetchData() {
    this.store.dispatch(new StartFetchRecipes());
    // this.dataStorageService.fetchRecipes().subscribe();
  }

  onLogout() {
    this.store.dispatch(new Logout());
  }

  ngOnDestroy() {
    this.userSub.unsubscribe();
  }
}
