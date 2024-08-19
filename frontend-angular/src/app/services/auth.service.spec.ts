import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { environment } from '../envinronments/envinronment';
import { UserLoginDto } from '../model/UserLoginDto';
import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
    localStorage.clear(); 
  });

  it('should create the service', () => {
    expect(service).toBeTruthy();
  });

  it('should set isLogged to true if token exists in localStorage on checkLogin', () => {
    localStorage.setItem('token', 'test-token');
    service.checkLogin().subscribe(isLogged => {
      expect(isLogged).toBeTrue();
    });
    expect(service.isLogged$.value).toBeTrue();
  });

  it('should set isLogged to false if token does not exist in localStorage on checkLogin', () => {
    service.checkLogin().subscribe(isLogged => {
      expect(isLogged).toBeFalse();
    });
    expect(service.isLogged$.value).toBeFalse();
  });

  it('should return the correct login status with isLoggedUser', () => {
    service.isLogged$.next(true);
    expect(service.isLoggedUser()).toBeTrue();
    service.isLogged$.next(false);
    expect(service.isLoggedUser()).toBeFalse();
  });

  it('should make a login request and store the token', () => {
    const mockLogin: UserLoginDto = { username: 'testuser', password: 'testpass' };
    const mockResponse = { token: 'test-token' };

    service.makeLogin(mockLogin).subscribe(() => {
      expect(localStorage.getItem('token')).toBe('test-token');
      expect(service.isLogged$.value).toBeTrue();
    });

    const req = httpMock.expectOne(`${environment.host}/auth/login`);
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);
  });

  it('should handle login errors correctly', () => {
    const mockLogin: UserLoginDto = { username: 'testuser', password: 'testpass' };

    service.makeLogin(mockLogin).subscribe({
      error: (error) => {
        expect(error.message).toBe('test');
      }
    });

    const req = httpMock.expectOne(`${environment.host}/auth/login`);
    expect(req.request.method).toBe('POST');
    req.flush({ message: 'Invalid credentials' }, { status: 401, statusText: 'Unauthorized' });
  });

  it('should clear localStorage and set isLogged to false on logout', () => {
    localStorage.setItem('token', 'test-token');
    service.isLogged$.next(true);

    service.logout();

    expect(localStorage.getItem('token')).toBeNull();
    expect(service.isLogged$.value).toBeFalse();
  });
});
