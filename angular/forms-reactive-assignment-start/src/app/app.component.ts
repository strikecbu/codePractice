import { Component, OnInit} from "@angular/core";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {Observable} from "rxjs";


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
      terms: new FormControl(null, Validators.requiredTrue),
      choice: new FormControl(null, [Validators.required]),
      sports: new FormArray(
        [new FormControl(null), new FormControl(null)],
        [Validators.required]
      ),
      email: new FormControl(null, [Validators.email]),
    });

    window["userForm"] = this.userForm;
  }

  sports: { id: number; name: string }[] = [
    {
      id: 0,
      name: "baseball",
    },
    { id: 1, name: "tennis" },
  ];

  onSubmit() {
    this.isSubmit = true;
    console.log(this.userForm);
    this.matchSportValues();

  }

  matchSportValues(): string[] {
    const values: string[] = [];
    for (let i = 0; i < this.userForm.value.sports.length; i++) {
      if (!this.userForm.value.sports[i]) continue;
      const sport = this.sports.find((item) => item.id === i);
      if (sport) {
        values.push(sport.name);
      }
    }
    console.log("Your choose sports: " + values);
    return values;
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
