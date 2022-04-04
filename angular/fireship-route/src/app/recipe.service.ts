import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, skip, switchMap, tap } from 'rxjs';

export interface Recipe {
  description: string;
  imagePath: string;
  ingredients: Ingredient[];
  name: string;
}

export interface Ingredient {
  amount: number;
  name: string;
}

@Injectable({ providedIn: 'root' })
export class RecipeService {
  private subject = new BehaviorSubject<Recipe[]>([]);

  recipes$ = this.subject.asObservable().pipe();

  constructor(private httpClient: HttpClient) {}

  getRecipes() {
    console.log('getRecipes...');
    this.httpClient
      .post<any>(
        'https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyBJbJ5FbJpautZxK3NAQ9iutkyl7XwNtGQ',
        {
          email: 'test@test.com',
          password: '123456',
          returnSecureToken: true,
        }
      )
      .pipe(
        switchMap((resp) => {
          return this.httpClient
            .get<Recipe[]>(
              'https://angular-http2-3d5ab-default-rtdb.asia-southeast1.firebasedatabase.app/recipes.json',
              {
                params: new HttpParams().set('auth', resp.idToken),
              }
            )
            .pipe(
              tap((recipes) => {
                this.subject.next(recipes);
              })
            );
        })
      )
      .subscribe();
  }
}
