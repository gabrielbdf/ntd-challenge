import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HeaderComponent } from '../../components/header/header.component';
import { OperationRequest } from '../../model/OperationRequest';
import { AuthService } from '../../services/auth.service';
import { OperationsService } from '../../services/operations.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatTabsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    MatDividerModule,
    HeaderComponent
  ],
  templateUrl: `home.component.html`,
  styleUrl: './home.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent implements OnInit {

  operationService = inject(OperationsService);
  authService = inject(AuthService);
  fb = inject(FormBuilder);
  operations: any[] = [];
  operationsForms: FormGroup[] = [];


  ngOnInit(): void {
    this.operationService.getOperations().subscribe((res) => {
      this.operations = res;
      res.forEach((operation) => {
        const group = this.fb.group({
          operation: new FormControl(operation[0]),
          args: new FormArray([]),
          result: 0
        })
        for (let i = 0; i < +operation[1]; i++) {
          (group.get('args') as FormArray).push(new FormControl(0));
        }
        this.operationsForms.push(group);
      });
    })
  }

  submit(operationForm: FormGroup) {
    if (operationForm.valid) {
      this.operationService.makeOperation(operationForm.value as OperationRequest).subscribe((res) => {
        operationForm.get('result')?.setValue(res.result);
      });
    }
  }

  getControls(args: AbstractControl<any, any> | null) {
    return (args as FormArray).controls;
  }

}
