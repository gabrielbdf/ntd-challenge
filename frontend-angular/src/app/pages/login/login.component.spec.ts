import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { UserLoginDto } from '../../model/UserLoginDto';
import { AuthService } from '../../services/auth.service';
import { LoginComponent } from './login.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('LoginComponent', () => {
    let component: LoginComponent;
    let fixture: ComponentFixture<LoginComponent>;
    let authServiceMock: any;
    let routerMock: any;

    beforeEach(async () => {
        authServiceMock = {
            checkLogin: jasmine.createSpy('checkLogin').and.returnValue(of(false)),
            makeLogin: jasmine.createSpy('makeLogin').and.returnValue(of({}))
        };

        routerMock = {
            navigate: jasmine.createSpy('navigate')
        };

        await TestBed.configureTestingModule({
            imports: [
                ReactiveFormsModule,
                RouterTestingModule,
                LoginComponent,
                NoopAnimationsModule],
            providers: [
                { provide: AuthService, useValue: authServiceMock },
                { provide: Router, useValue: routerMock }
            ]
        })
            .compileComponents();

        fixture = TestBed.createComponent(LoginComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should initialize form with default values', () => {
        expect(component.formLogin.value).toEqual({
            username: 'admin',
            password: 'admin'
        });
    });

    it('should call checkLogin on initialization', () => {
        expect(authServiceMock.checkLogin).toHaveBeenCalled();
    });


    it('should not navigate if not logged in', () => {
        authServiceMock.checkLogin.and.returnValue(of(false));
        component.ngOnInit();
        expect(routerMock.navigate).not.toHaveBeenCalled();
    }); 

    it('should call authService.makeLogin on valid form submission', () => {
        component.formLogin.setValue({
            username: 'testuser',
            password: 'testpass'
        });

        component.makeLogin();

        const expectedDto: UserLoginDto = {
            username: 'testuser',
            password: 'testpass'
        };
        expect(authServiceMock.makeLogin).toHaveBeenCalledWith(expectedDto);
        expect(routerMock.navigate).toHaveBeenCalledWith(['']);
    });

    it('should set form errors on invalid login', () => {
        authServiceMock.makeLogin.and.returnValue(throwError({ status: 401 }));

        component.formLogin.setValue({
            username: 'testuser',
            password: 'wrongpass'
        });

        component.makeLogin();

        expect(component.formLogin.errors).toEqual({ invalidLogin: true });
        expect(routerMock.navigate).not.toHaveBeenCalled();
    });
});
