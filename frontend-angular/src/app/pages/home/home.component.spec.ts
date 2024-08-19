import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule, FormArray, FormGroup } from '@angular/forms';
import { of } from 'rxjs';
import { HomeComponent } from './home.component';
import { OperationsService } from '../../services/operations.service';
import { AuthService } from '../../services/auth.service';
import { OperationRequest } from '../../model/OperationRequest';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let operationsServiceMock: any;
  let authServiceMock: any;

  beforeEach(async () => {
    operationsServiceMock = {
      getOperations: jasmine.createSpy('getOperations').and.returnValue(of([
        ['ADD', 2],
        ['MULTIPLY', 3]
      ])),
      makeOperation: jasmine.createSpy('makeOperation').and.returnValue(of({ result: 10 }))
    };

    authServiceMock = jasmine.createSpyObj('AuthService', ['checkLogin']);

    await TestBed.configureTestingModule({
      imports: [
        HomeComponent, 
        ReactiveFormsModule,
        RouterTestingModule,
        NoopAnimationsModule
    ], 

      providers: [
        { provide: OperationsService, useValue: operationsServiceMock },
        { provide: AuthService, useValue: authServiceMock },
        FormBuilder
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize operations and forms on ngOnInit', () => {
    expect(operationsServiceMock.getOperations).toHaveBeenCalled();
    expect(component.operations.length).toBe(2);
    expect(component.operationsForms.length).toBe(2);

    expect(component.operationsForms[0].get('operation')?.value).toBe('ADD');
    expect((component.operationsForms[0].get('args') as FormArray).length).toBe(2);
    expect(component.operationsForms[1].get('operation')?.value).toBe('MULTIPLY');
    expect((component.operationsForms[1].get('args') as FormArray).length).toBe(3);
  });

  it('should submit the operation and set the result', () => {
    const operationForm = component.operationsForms[0];
    operationForm.setValue({
      operation: 'ADD',
      args: [1, 2],
      result: 0
    });

    component.submit(operationForm);

    expect(operationsServiceMock.makeOperation).toHaveBeenCalledWith({
      operation: 'ADD',
      args: [1, 2],
      result: 0
    } as OperationRequest);

    expect(operationForm.get('result')?.value).toBe(10);
  });


  it('getControls should return the controls from FormArray', () => {
    const formArray = new FormArray([new FormGroup({}), new FormGroup({})]);
    const controls = component.getControls(formArray);
    expect(controls.length).toBe(2);
  });
});
