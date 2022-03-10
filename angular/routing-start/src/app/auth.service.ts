import {Injectable} from '@angular/core';

@Injectable()
export class AuthService {

  canAccess = false;

  auth() {
    return new Promise<boolean>(((resolve, reject) => {
      setTimeout(() => {
        resolve(this.canAccess);
      }, 800);
    }));
  }

  onLogin() {
    this.canAccess = true;
  }

  onLogout() {
    this.canAccess = false;
  }

}
