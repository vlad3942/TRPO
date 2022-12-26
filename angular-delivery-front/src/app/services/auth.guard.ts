import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {TokenStorageService} from "./token-storage.service";
import {Observable} from "rxjs";

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(
    private token: TokenStorageService,
    private router: Router
  ) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    if (!!this.token.getToken()) {
      return true;
    } else {
      this.token.signOut()
      this.router.navigate(['/login'], {
        queryParams: {
          badLogin: true
        }
      })
      return false
    }
  }

}
