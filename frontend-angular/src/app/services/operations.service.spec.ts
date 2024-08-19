import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { environment } from '../envinronments/envinronment';
import { ListDetails } from '../model/ListDetails';
import { OperationRequest } from '../model/OperationRequest';
import { AuthService } from './auth.service';
import { OperationsService } from './operations.service';

describe('OperationsService', () => {
    let service: OperationsService;
    let httpMock: HttpTestingController;
    let authServiceMock: any;

    beforeEach(() => {
        authServiceMock = {
            userDetails: {
                next: jasmine.createSpy('next'),
                value: { balance: 100 }
            }
        };

        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [
                OperationsService,
                { provide: AuthService, useValue: authServiceMock }
            ]
        });

        service = TestBed.inject(OperationsService);
        httpMock = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should retrieve operations and update user details', () => {
        const mockResponse: ListDetails = {
            userDetails: { username: 'admin', balance: 200 },
            operations: [['ADD', '2'], ['SUBTRACT', '2']]
        };

        service.getOperations().subscribe(operations => {
            expect(operations).toEqual([['ADD', '2'], ['SUBTRACT', '2']]);
            expect(authServiceMock.userDetails.next).toHaveBeenCalledWith(mockResponse.userDetails);
        });

        const req = httpMock.expectOne(`${environment.host}/operation/list`);
        expect(req.request.method).toBe('GET');
        req.flush(mockResponse);
    });

    it('should retrieve user history', () => {
        const mockHistory = [
            { id: 1, operation: 'ADD', amount: 10 },
            { id: 2, operation: 'SUBTRACT', amount: 5 }
        ];

        service.getUserhistory().subscribe(history => {
            expect(history).toEqual(mockHistory);
        });

        const req = httpMock.expectOne(`${environment.host}/operation/history`);
        expect(req.request.method).toBe('GET');
        req.flush(mockHistory);
    });

    it('should make operation and update user balance', () => {
        const operationRequest: OperationRequest = { operation: 'ADD', args: [1, 2], result: 0 , userBalance: 150 };
        const mockResponse = { operation: 'ADD', args: [1, 2], userBalance: 150, result: 2 };

        service.makeOperation(operationRequest).subscribe(response => {
            expect(response).toEqual(mockResponse);
            expect(authServiceMock.userDetails.next).toHaveBeenCalledWith({
                balance: 150
            });
        });

        const req = httpMock.expectOne(`${environment.host}/operation/enum`);
        expect(req.request.method).toBe('POST');
        expect(req.request.headers.get('Content-Type')).toBe('application/json');
        expect(req.request.body).toEqual(operationRequest);
        req.flush(mockResponse);
    });
});
