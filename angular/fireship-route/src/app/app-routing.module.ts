import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RecipeAdminGuard } from './recipe-admin.guard';
import { RecipeDetailComponent } from './recipe-detail/recipe-detail.component';
import { RecipeResolveGuard } from './recipe-resolve.guard';
import { RecipeComponent } from './recipe/recipe.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'recipes',
    component: RecipeComponent,
    canActivate: [RecipeAdminGuard],
    children: [
      {
        path: ':name',
        component: RecipeDetailComponent,
        resolve: [RecipeResolveGuard],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
