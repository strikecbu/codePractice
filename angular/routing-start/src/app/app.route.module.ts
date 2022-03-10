import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {ServersComponent} from './servers/servers.component';
import {ServerComponent} from './servers/server/server.component';
import {EditServerComponent} from './servers/edit-server/edit-server.component';
import {UsersComponent} from './users/users.component';
import {UserComponent} from './users/user/user.component';
import {PageNotFoundComponent} from './page-not-found/page-not-found.component';
import {AuthService} from './auth.service';
import {AuthGuard} from './auth-guard.service';
import {CanDeactivateGuard} from './can-deactivate.guard';
import {ServerResolver} from './servers/server.resolver';

const routerApps: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {
    path: 'servers', component: ServersComponent, canActivateChild: [AuthGuard], children: [
      {path: ':id', component: ServerComponent, resolve: {server: ServerResolver}},
      {path: ':id/edit', component: EditServerComponent, canDeactivate: [CanDeactivateGuard]},
    ]
  },
  {
    path: 'users', component: UsersComponent, children: [
      {path: ':id/:name', component: UserComponent},
    ]
  }, {
    path: 'not-found', component: PageNotFoundComponent
  }, {
    path: '**', redirectTo: 'not-found'
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routerApps)
  ],
  providers: [AuthService, AuthGuard, CanDeactivateGuard, ServerResolver],
  exports: [RouterModule]
})
export class AppRouteModule {

}
