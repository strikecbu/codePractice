import { Component } from "@angular/core";
import { NgForm } from "@angular/forms";
import {AuthService, signApiResponse} from "./auth.service";
import {Observable} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: "app-auth",
  templateUrl: "./auth.component.html",
})
export class AuthComponent {
  isLoginMode = false;
  isLoading = false;
  error: string = null;
  authObs: Observable<signApiResponse>;

  constructor(private authService: AuthService, private router: Router) {}

  switchButton() {
    this.isLoginMode = !this.isLoginMode;
  }

  submit(form: NgForm) {
    if (!form.valid) {
      return;
    }
    this.isLoading = true;
    if (this.isLoginMode) {
      this.authObs = this.authService.login(form.value.email, form.value.password);
    } else {
      this.authObs = this.authService.signUp(form.value.email, form.value.password);
    }
    this.authObs.subscribe(
      (response) => {
        console.log(response);
        this.isLoading = false;
        this.router.navigate(['/recipes'])
      },
      (errorMessage) => {
        this.error = errorMessage;
        this.isLoading = false;
      }
    );
    form.reset();
  }
}
