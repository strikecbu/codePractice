import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { first, Observable, tap } from 'rxjs';
import { RecipeService } from '../recipe.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  constructor(private authService: AuthService) {}

  auth$ = this.authService.auth$;

  ngOnInit(): void {}

  toggleAuth() {
    this.auth$
      .pipe(
        first(),
        tap((auth) => {
          if (auth) {
            this.authService.removeAuth();
          } else {
            this.authService.setAuth();
          }
        })
      )
      .subscribe();
  }
}
