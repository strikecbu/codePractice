import { Component, OnDestroy, OnInit } from "@angular/core";

import { DataStorageService } from "../shared/data-storage.service";
import { AuthService } from "../auth/auth.service";
import { Subscription } from "rxjs";
import { Router } from "@angular/router";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
})
export class HeaderComponent implements OnInit, OnDestroy {
  constructor(
    private dataStorageService: DataStorageService,
    private authService: AuthService,
    private router: Router
  ) {}

  isAuthentication = false;
  authSub: Subscription;

  onSaveData() {
    this.dataStorageService.storeRecipes();
  }

  onFetchData() {
    this.dataStorageService.fetchRecipes().subscribe();
  }

  ngOnInit(): void {
    this.authSub = this.authService.userSubj.subscribe((user) => {
      this.isAuthentication = !!user;
    });
  }

  ngOnDestroy(): void {
    this.authSub.unsubscribe();
  }

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/auth']);
  }
}
