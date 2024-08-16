import { HttpEvent, HttpHandlerFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { catchError, Observable, switchMap } from "rxjs";
import { AuthService } from "./auth.service";
import { Router } from "@angular/router";


export function httpInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {

    const auth = inject(AuthService);
    const router = inject(Router);
    const isLogged = auth.isLoggedUser();
    if (isLogged) {
        const reqWithHeader = req.clone({
            headers: req.headers.set('Authorization', `Bearer ${localStorage.getItem('token')}`),
        });
        return next(reqWithHeader).pipe(
            catchError((error) => {
                auth.logout();
                router.navigate(['/login']);
                return new Observable<HttpEvent<unknown>>();
            })
        );
    }

    return next(req);
}