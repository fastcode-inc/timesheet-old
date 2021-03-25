
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UsertaskService } from 'src/app/entities/usertask/usertask.service';
import { Observable } from 'rxjs';
import { IUsertask } from 'src/app/entities/usertask/iusertask';
import { catchError, map } from 'rxjs/operators';

export interface ITaskModel extends IUsertask  {  		
	projectDescriptiveField?: string;
	projectid: number;
	customerDescriptiveField?: string;
	customerid: number;
}

@Injectable({
  providedIn: 'root'
})
export class UsertaskExtendedService extends UsertaskService {
	constructor(protected httpclient: HttpClient) { 
    super(httpclient);
    this.url += '/extended';
  }

  public getTasks(): Observable<ITaskModel[]> {
    return this.http.get<ITaskModel[]>(this.url + "/taskList").pipe(map((response: any) => {
      return response;
    }), catchError(this.handleError));
  }
}
