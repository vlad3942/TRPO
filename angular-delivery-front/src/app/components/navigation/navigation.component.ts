import {Component, Input, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {TokenStorageService} from "../../services/token-storage.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  @Input() isLoggedIn = false

  constructor(public tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.tokenStorageService.isLoggedIn.subscribe(value => {
      this.isLoggedIn = value;
    });
  }

  logout(): void {
    this.tokenStorageService.signOut();
    if (environment.production) {
      window.location.href = "/";
    } else {
      window.location.reload();
    }
  }
}
