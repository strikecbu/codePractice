import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map, Observable, of, switchMap, tap, withLatestFrom } from 'rxjs';
import { Recipe, RecipeService } from '../recipe.service';

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.css'],
})
export class RecipeDetailComponent implements OnInit {
  constructor(
    private recipeService: RecipeService,
    private activeRoute: ActivatedRoute
  ) {}

  recipe$?: Observable<Recipe>;

  ngOnInit(): void {
    this.recipe$ = this.activeRoute.data.pipe(
      tap((data) => {
        console.log(data);
      }),
      switchMap((data) => {
        return of(data['0'] as Recipe);
      })
    );

    // this.recipe$ = this.activeRoute.data
    // this.recipe$ = this.activeRoute.paramMap.pipe(
    //   withLatestFrom(this.recipeService.recipes$),
    //   switchMap(([paramMap, recipes]) => {
    //     const index = recipes.findIndex(
    //       (item) => item.name === paramMap.get('name')
    //     );
    //     return of(recipes[index]);
    //   })
    // );
    // this.recipe$ = this.recipeService.recipes$.pipe(
    //   // withLatestFrom(this.activeRoute.paramMap),
    //   map((recipes) => {
    //     const index = recipes.findIndex(
    //       (item) => item.name === this.activeRoute.snapshot.paramMap.get('name')
    //     );
    //     return recipes[index];
    //   })
    // );
  }
}
