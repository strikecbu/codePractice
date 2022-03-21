import {
  Component,
  ComponentFactoryResolver,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { NgForm } from "@angular/forms";
import { Subscription } from "rxjs";
import { Store } from "@ngrx/store";

import { AlertComponent } from "../shared/alert/alert.component";
import { PlaceholderDirective } from "../shared/placeholder/placeholder.directive";
import * as fromApp from "../store/app.reducer";
import * as AuthActions from "./store/auth.actions";
import { map } from "rxjs/operators";

@Component({
  selector: "app-auth",
  templateUrl: "./auth.component.html",
})
export class AuthComponent implements OnInit, OnDestroy {
  isLoginMode = true;
  isLoading$ = this.store.select("auth").pipe(map((state) => state.isLoading));
  @ViewChild(PlaceholderDirective, { static: false })
  alertHost: PlaceholderDirective;

  private closeSub: Subscription;
  private errorSub: Subscription;

  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    private store: Store<fromApp.AppState>
  ) {}

  ngOnInit() {
    this.errorSub = this.store
      .select("auth")
      .pipe(map((state) => state.errorMsg))
      .subscribe((errorMsg) => {
        if (errorMsg) {
          this.showErrorAlert(errorMsg);
        }
      });
  }

  onSwitchMode() {
    this.isLoginMode = !this.isLoginMode;
  }

  onSubmit(form: NgForm) {
    if (!form.valid) {
      return;
    }
    const email = form.value.email;
    const password = form.value.password;

    if (this.isLoginMode) {
      this.store.dispatch(new AuthActions.StartLogin({ email, password }));
    } else {
      this.store.dispatch(new AuthActions.StartSignup({ email, password }));
    }
    form.reset();
  }

  ngOnDestroy() {
    if (this.closeSub) {
      this.closeSub.unsubscribe();
      this.errorSub.unsubscribe();
    }
  }

  private showErrorAlert(message: string) {
    // const alertCmp = new AlertComponent();
    const alertCmpFactory =
      this.componentFactoryResolver.resolveComponentFactory(AlertComponent);
    const hostViewContainerRef = this.alertHost.viewContainerRef;
    hostViewContainerRef.clear();

    const componentRef = hostViewContainerRef.createComponent(alertCmpFactory);

    componentRef.instance.message = message;
    this.closeSub = componentRef.instance.close.subscribe(() => {
      this.store.dispatch(new AuthActions.ClearError());
      this.closeSub.unsubscribe();
      hostViewContainerRef.clear();
    });
  }
}
