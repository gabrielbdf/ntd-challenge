import { HttpClient } from '@angular/common/http';
import { Component, inject, OnInit } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { OperationsService } from '../../services/operations.service';
import { MatTableModule } from '@angular/material/table';
import { Observable, of } from 'rxjs';
import { OperationHistory } from '../../model/OperationHistory';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [
    HeaderComponent,
    MatTableModule,
    CommonModule
  ],
  templateUrl: './history.component.html',
  styleUrl: './history.component.scss'
})
export class HistoryComponent implements OnInit {

  operationService = inject(OperationsService);
  http = inject(HttpClient);


  displayedColumns: string[] = ['id', 'operation', 'amount', 'response', 'timestamp', 'userBalance'];
  dataSource: Observable<OperationHistory[]> = of([]);

  ngOnInit(): void {
    this.dataSource = this.operationService.getUserhistory()
  }



}
