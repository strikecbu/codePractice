import {Component, ViewChild} from '@angular/core';
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  @ViewChild('f') userForm: NgForm;

  userData: {
    email: string
    project: string
    password: string
  };
  isSubmit = false;

  onSubmit(form: NgForm) {
    if (form.valid) {
      this.isSubmit = true;
      console.log(form);
      this.userData = {
        email: form.form.value.email,
        project: form.form.value.project,
        password: form.form.value.password,
      }
      // form.form.reset();
    }
  }

  onSuggest() {
    this.userForm.form.patchValue({
      project: 'Advanced'
    })
  }
}
