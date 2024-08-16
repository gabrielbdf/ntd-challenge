import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map } from 'rxjs';
import { tap } from 'rxjs/internal/operators/tap';
import { environment } from '../envinronments/envinronment';
import { ListDetails } from '../model/ListDetails';
import { OperationRequest } from '../model/OperationRequest';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class OperationsService {

  private http = inject(HttpClient);
  private authService = inject(AuthService);


  getOperations() {
    return this.http.get<ListDetails>(`${environment.host}/operation/list`)
      .pipe(
        tap((res) => this.authService.userDetails.next(res.userDetails)),
        map((res) => res.operations)
      )
  }

  makeOperation(operationRequest: OperationRequest) {
    return this.http.post<OperationRequest>(`${environment.host}/operation/enum`,
      operationRequest,
      {
        headers: {
          'Content-Type': 'application/json'
        }
      }
    );
  }



}
