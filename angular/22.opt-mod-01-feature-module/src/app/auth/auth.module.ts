import {NgModule} from "@angular/core";
import {AuthComponent} from "./auth.component";
import {SharedModule} from "../shared/shared.module";
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AuthComponent
  ],
  imports: [
    SharedModule,
    FormsModule,
    RouterModule.forChild([{ path: "auth", component: AuthComponent }])
  ]
})
export class AuthModule {}
