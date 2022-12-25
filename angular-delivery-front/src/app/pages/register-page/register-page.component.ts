import { Component } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {IRegister} from "../../models/register";
import {Observable} from "rxjs";

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent {

  form = new FormGroup({
    login: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(4),
      Validators.maxLength(20)
    ]),
    email: new FormControl<string>('', [
      Validators.required,
      Validators.email,
    ]),
    phone: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(11),
      Validators.maxLength(12),
    ]),
    password: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(8)
    ])
  })

  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService) {
  }

  onSubmit(): void {
    let data: IRegister = {
      username: this.form.value.login as string,
      email: this.form.value.email as string,
      phone: this.form.value.phone as string,
      password: this.form.value.password as string,
      roles:["user"]
    }

    if(data.username === '' || data.phone === '' || data.email === '' || data.password === '') {
      this.isSuccessful = false
      return
    }

    this.authService.register(data).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

  get login() {
    return this.form.controls.login as FormControl
  }
  get email() {
    return this.form.controls.email as FormControl
  }
  get phone() {
    return this.form.controls.phone as FormControl
  }
  get password() {
    return this.form.controls.password as FormControl
  }
}
