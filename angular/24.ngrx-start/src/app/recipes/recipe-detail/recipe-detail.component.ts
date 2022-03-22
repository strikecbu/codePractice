import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";

import { Recipe } from "../recipe.model";
import { RecipeService } from "../recipe.service";
import { Store } from "@ngrx/store";
import { AppState } from "../../store/app.reducer";
import { DeleteRecipe } from "../store/recipe.actions";
import { map, switchMap, tap } from "rxjs/operators";
import {AddIngredients} from "../../shopping-list/store/shopping-list.actions";

@Component({
  selector: "app-recipe-detail",
  templateUrl: "./recipe-detail.component.html",
  styleUrls: ["./recipe-detail.component.css"],
})
export class RecipeDetailComponent implements OnInit {
  recipe: Recipe;
  id: number;

  constructor(
    private recipeService: RecipeService,
    private route: ActivatedRoute,
    private router: Router,
    private store: Store<AppState>
  ) {}

  ngOnInit() {
    this.route.params
      .pipe(
        tap((params) => {
          this.id = +params["id"];
        }),
        switchMap((params) => {
          return this.store.select("recipe").pipe(
            map((state) => state.recipes),
            map((recipes) => {
              return recipes.find((recipe, index) => index === this.id);
            })
          );
        })
      )
      .subscribe((recipe) => (this.recipe = recipe));
  }

  onAddToShoppingList() {
    this.store.dispatch(new AddIngredients(this.recipe.ingredients));
    // this.recipeService.addIngredientsToShoppingList(this.recipe.ingredients);
  }

  onEditRecipe() {
    this.router.navigate(["edit"], { relativeTo: this.route });
    // this.router.navigate(['../', this.id, 'edit'], {relativeTo: this.route});
  }

  onDeleteRecipe() {
    this.store.dispatch(new DeleteRecipe(this.id));
    // this.recipeService.deleteRecipe(this.id);
    this.router.navigate(["/recipes"]);
  }
}
