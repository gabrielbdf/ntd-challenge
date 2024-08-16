import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { AuthService } from './services/auth.service';
import { map } from 'rxjs/internal/operators/map';
import { HistoryComponent } from './pages/history/history.component';


export const canActivate: CanActivateFn = (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
) => {
    const authService = inject(AuthService);
    const router = inject(Router);

    return authService.checkLogin().pipe(
        map((isLogged) => {
            if (isLogged) {
                return true;
            } else {
                router.navigate(['/login']);
                return false;
            }
        })
    );

};

export const routes: Routes = [
    {
        path: '', redirectTo: 'home', pathMatch: 'full'
    },
    {
        path: 'home',
        component: HomeComponent,
        canActivate: [canActivate]
    },
    {
        path: 'history',
        component: HistoryComponent,
        canActivate: [canActivate]
    },
    {
        path: 'login', component: LoginComponent
    }
];
