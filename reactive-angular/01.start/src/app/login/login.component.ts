import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Router } from '@angular/router';
import { AuthStore } from '../auth/auth.store';
import { take } from 'rxjs/operators';
import { noop } from 'rxjs';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authStore: AuthStore
  ) {
    this.form = fb.group({
      email: ['test@angular-university.io', [Validators.required]],
      password: ['test', [Validators.required]],
    });
  }

  ngOnInit() {}

  login() {
    const val = this.form.value;
    this.authStore
      .login(val.email, val.password)
      .pipe(take(1))
      .subscribe(
        () => {
          this.router.navigate(['/']);
        },
        (error) => {
          alert('Login is failed...');
        }
      );
  }
}
