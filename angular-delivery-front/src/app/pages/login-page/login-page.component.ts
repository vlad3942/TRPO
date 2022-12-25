import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from "../../services/token-storage.service";
import {ILogin} from "../../models/login";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  form = new FormGroup({
    login: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(4),
      Validators.maxLength(20)
    ]),
    password: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(8)
    ])
  });
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  role: string = '';

  constructor(
    private authService: AuthService,
    private tokenStorage: TokenStorageService
  ) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.role = this.tokenStorage.getUser().roles[0];
      this.tokenStorage.isLoggedIn.next(true);
    }
  }

  onSubmit(): void {

    let data: ILogin = {
      username: this.form.value.login as string,
      password: this.form.value.password as string
    }

    this.authService.login(data).subscribe(
      data => {
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.role = this.tokenStorage.getUser().role;
        this.reloadPage();
      },
      err => {
        debugger;
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  reloadPage(): void {
    window.location.reload();
  }

  get login() {
    return this.form.controls.login as FormControl
  }

  get password() {
    return this.form.controls.password as FormControl
  }
}
