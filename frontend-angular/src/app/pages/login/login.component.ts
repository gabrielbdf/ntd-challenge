import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, inject, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { UserLoginDto } from '../../model/UserLoginDto';
import { AuthService } from '../../services/auth.service';
import { take } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent implements OnInit {

  authService = inject(AuthService);
  router = inject(Router);
  isAuth = this.authService.checkLogin();

  formLogin = new FormGroup({
    username: new FormControl('admin', { nonNullable: true }),
    password: new FormControl('admin', { nonNullable: true })
  });

  ngOnInit(): void {
    this.isAuth.pipe(take(1))
      .subscribe
      ({
        next: (isLogged) => {
          if (isLogged) {
            this.router.navigate(['']);
          }
        }
      });
  }

  makeLogin() {
    if (this.formLogin.valid) {
      const loginDto: UserLoginDto = {
        username: this.formLogin.value.username ?? '',
        password: this.formLogin.value.password ?? ''
      };
      this.authService.makeLogin(loginDto).subscribe({
        next: () => {
          this.router.navigate(['']);
        },
        error: (err) => {
          this.formLogin.setErrors({ invalidLogin: true });
        }
      });
    }
  }
}
