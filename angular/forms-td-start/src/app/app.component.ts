import {Component, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  @ViewChild('f') userForm: NgForm;
  questionAnswer: string;
  isSubmit: boolean;
  userFormData: {
    username: string,
    email: string,
    secret: string,
    answer: string
  };

  suggestUserName() {
    const suggestedName = 'Superuser';
    this.userForm.form.patchValue({
      userData: {
        username: suggestedName,
        email: 'email@cdddsddo.com '
      },
      secret: 'teacher',
      answer: 'hoho'
    });

  }

  onSubmit(form: NgForm) {
    this.isSubmit = true;
    this.userFormData = {
      username: form.form.value.userData.username,
      email: form.form.value.userData.email,
      secret: form.form.value.secret,
      answer: form.form.value.answer
    };

    console.log(form);
    console.log(form.form.valid);
    this.onReset();

  }

  onReset() {
    this.userForm.reset();
  }
}
