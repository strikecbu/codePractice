import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormControl,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { delay, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  genders = ['male', 'female'];
  forbiddenNames = ['Test', 'Choco'];
  userDataForm: FormGroup;
  timer: number;

  constructor(private httpClient: HttpClient) {}

  ngOnInit(): void {
    this.userDataForm = new FormGroup({
      userData: new FormGroup({
        username: new FormControl(null, [
          Validators.required,
          this.forbiddenNamesCheck.bind(this),
        ]),
        email: new FormControl(
          null,
          [Validators.required, Validators.email],
          this.emailCheck.bind(this)
        ),
      }),
      gender: new FormControl('male', Validators.required),
      hobbies: new FormArray([]),
    });
  }

  onSubmit() {
    console.log(this.userDataForm.value);
    this.userDataForm.reset({ gender: 'male' });
  }

  onAddHobbies() {
    const control = new FormControl(null, Validators.required);
    (this.userDataForm.get('hobbies') as FormArray).push(control);
  }

  getControls() {
    return (this.userDataForm.get('hobbies') as FormArray).controls;
  }

  forbiddenNamesCheck(control: FormControl): { [s: string]: boolean } {
    if (this.forbiddenNames.indexOf(control.value) !== -1) {
      return { nameIsForbidden: true };
    }
    return null;
  }

  emailCheck(control: FormControl): Promise<any> | Observable<any> {
    return new Promise((resolve, reject) => {
      if (this.timer) {
        clearTimeout(this.timer);
      }
      console.log('Execute');
      this.timer = setTimeout(() => {
        if ('test@test.test' === control.value) {
          resolve({ emailInvalid: true });
        } else {
          resolve(null);
        }
      }, 1500);
    });
  }

  // sample use http to validate
  serverValidateElectricNo(
    control: AbstractControl
  ): Observable<ValidationErrors | null> {
    const url = 'http://localhost:8014/mgrfunc14/utystep4handler/submitData';

    return of(control.value).pipe(
      delay(300),
      switchMap((value) => {
        console.log('let go validate');
        const params = new HttpParams().set('ELECTRIC_NO', value);
        return this.httpClient.post<{ faliColumn: string }>(url, params, {});
      }),
      switchMap((resp) => {
        if (resp.faliColumn) {
          console.log('bad value');
          return of({ electricNoNotCorrect: true });
        }
        return of(null);
      })
    );
  }

  onSuggestName() {
    this.userDataForm.patchValue({
      userData: {
        username: 'Andy',
      },
    });
  }
}
