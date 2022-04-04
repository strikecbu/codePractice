import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Resolve,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { map, Observable, take } from 'rxjs';
import { Recipe, RecipeService } from './recipe.service';

@Injectable({
  providedIn: 'root',
})
export class RecipeResolveGuard implements Resolve<Recipe> {
  constructor(private recipeService: RecipeService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<Recipe> | Promise<Recipe> | Recipe {
    return this.recipeService.recipes$.pipe(
      take(1), //一定要有，resolve會等到complete完才遞交資料
      map((recipes) => {
        const index = recipes.findIndex(
          (item) => item.name === route.paramMap.get('name')
        );
        return recipes[index];
      })
    );
  }
}
