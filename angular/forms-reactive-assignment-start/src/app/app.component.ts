import { OnInit } from "@angular/core";
import { Component } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Observable } from "rxjs";

// import { FormGroup } from '@angular/forms'

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent implements OnInit {
  userForm: FormGroup;
  forbiddenProjectNames = ["Test"];
  isSubmit = false;
  userData: {
    project: {
      name: string;
      status: string;
    };
    email: string;
  };

  ngOnInit(): void {
    this.userForm = new FormGroup({
      project: new FormGroup({
        name: new FormControl(
          null,
          [Validators.required],
          this.checkNameValid.bind(this)
        ),
        status: new FormControl(null, Validators.required),
      }),
      email: new FormControl(null, [Validators.email, Validators.required]),
    });
  }

  onSubmit() {
    this.isSubmit = true;
    console.log(this.userForm);
  }

  checkNameValid(control: FormControl): Observable<any> | Promise<any> {
    return new Promise((resolve) => {
      setTimeout(() => {
        if (this.forbiddenProjectNames.indexOf(control.value) != -1) {
          resolve({ forbiddenNames: true });
        } else {
          resolve(null);
        }
      }, 1500);
    });
  }
}
