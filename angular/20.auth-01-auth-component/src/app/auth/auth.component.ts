import {Component, ComponentFactoryResolver, OnDestroy, ViewChild} from "@angular/core";
import { NgForm } from "@angular/forms";
import {AuthService, signApiResponse} from "./auth.service";
import {Observable, Subscription} from "rxjs";
import {Router} from "@angular/router";
import {PlaceholderDirective} from "../shared/placeholder.directive";
import {AlertComponent} from "../shared/alert/alert.component";

@Component({
  selector: "app-auth",
  templateUrl: "./auth.component.html",
})
export class AuthComponent implements OnDestroy{

  constructor(private authService: AuthService, private router: Router, private componentResolver: ComponentFactoryResolver) {}

  @ViewChild(PlaceholderDirective, {static: false}) placeHolder: PlaceholderDirective;
  isLoginMode = false;
  isLoading = false;
  error: string = null;
  authObs: Observable<signApiResponse>;
  alertSubs: Subscription;

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
        this.createAlertMessage(errorMessage);
        this.isLoading = false;
      }
    );
    form.reset();
  }
  onCloseHandle() {
    this.error = null;
  }

  createAlertMessage(message: string) {

    const viewRef = this.placeHolder.viewRef;
    const alertCmpFactory = this.componentResolver.resolveComponentFactory(AlertComponent);
    const cmpRef = viewRef.createComponent(alertCmpFactory);

    cmpRef.instance.message = message;
    this.alertSubs = cmpRef.instance.close.subscribe(() => {
      viewRef.clear();
      this.alertSubs.unsubscribe()
    })
  }

  ngOnDestroy(): void {
    if (this.alertSubs) {
      this.alertSubs.unsubscribe();
    }
  }
}
