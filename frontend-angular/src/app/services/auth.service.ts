import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, throwError } from 'rxjs';
import { UserLoginDto } from '../model/UserLoginDto';
import { environment } from '../envinronments/envinronment';
import { UserDetails } from '../model/UserDetails';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private http = inject(HttpClient);
  private isLogged = new BehaviorSubject<boolean>(false);
  public userDetails = new BehaviorSubject<UserDetails>({
    balance: 0,
    username: ""
  });


  checkLogin() {
    if (localStorage.getItem('token')) {
      this.isLogged.next(true);
    }
    return this.isLogged.asObservable();
  }

  isLoggedUser() {
    return this.isLogged.value;
  }

  makeLogin(login: UserLoginDto) {
    return this.http.post(`${environment.host}/auth/login`, login)
      .pipe(
        catchError(() => throwError(() => new Error('test'))),
        map((response: any) => {
          if (response.token) {
            localStorage.setItem('token', response.token);
            this.isLogged.next(true);
          }
        }
        ));
  }

  logout() {
    localStorage.clear();
    this.isLogged.next(false);
  }

  get isLogged$(): BehaviorSubject<boolean> {
    return this.isLogged;
  }

  set isLogged$(value: boolean) {
    this.isLogged.next(value);
  }

}
