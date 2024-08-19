import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatTableModule } from '@angular/material/table';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { OperationHistory } from '../../model/OperationHistory';
import { OperationsService } from '../../services/operations.service';
import { HistoryComponent } from './history.component';


describe('HistoryComponent', () => {
    let component: HistoryComponent;
    let fixture: ComponentFixture<HistoryComponent>;
    let operationsServiceMock: any;

    beforeEach(async () => {
        const mockHistory: OperationHistory[] = [
            { id: 1, operation: 'ADD', amount: 10, response: '10', timestamp: new Date('2024-08-19T10:00:00Z'), userBalance: 100 },
            { id: 2, operation: 'SUBTRACT', amount: 5, response: '5', timestamp: new Date('2024-08-19T11:00:00Z'), userBalance: 95 }
        ];

        operationsServiceMock = {
            getUserhistory: jasmine.createSpy('getUserhistory').and.returnValue(of(mockHistory))
        };

        await TestBed.configureTestingModule({
            imports: [
                HistoryComponent, // Import the standalone component
                HttpClientModule, // Provide HttpClient
                MatTableModule,
                CommonModule,
                RouterTestingModule,
                NoopAnimationsModule

            ],
            providers: [
                { provide: OperationsService, useValue: operationsServiceMock }
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(HistoryComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should have displayedColumns defined', () => {
        expect(component.displayedColumns).toEqual(['id', 'operation', 'amount', 'response', 'timestamp', 'userBalance']);
    });

    it('should fetch and assign user history to dataSource on ngOnInit', () => {
        component.ngOnInit();
        fixture.detectChanges();

        component.dataSource.subscribe((data) => {
            expect(data.length).toBe(2);
            expect(data).toEqual([
                { id: 1, operation: 'ADD', amount: 10, response: '10', timestamp: new Date('2024-08-19T10:00:00Z'), userBalance: 100 },
                { id: 2, operation: 'SUBTRACT', amount: 5, response: '5', timestamp: new Date('2024-08-19T11:00:00Z'), userBalance: 95 }
            ]);
        });

        expect(operationsServiceMock.getUserhistory).toHaveBeenCalled();
    });

    it('should initialize dataSource as an observable', () => {
        expect(component.dataSource).toBeTruthy();
    });
});
