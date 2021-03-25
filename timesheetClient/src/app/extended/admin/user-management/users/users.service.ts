
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UsersService } from 'src/app/admin/user-management/users/users.service';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { IUsers } from 'src/app/admin/user-management/users';
import { ISearchField, ServiceUtils } from 'src/app/common/shared';
@Injectable({
  providedIn: 'root'
})
export class UsersExtendedService extends UsersService {
	constructor(protected httpclient: HttpClient) { 
    super(httpclient);
    this.url += '/extended';
  }

  public setReminder(input): Observable<any> {
    return this.httpclient
      .post(this.url + '/reminder', input).pipe(catchError(this.handleError));
  }

  public getReminderDetails(): Observable<any> {
    return this.httpclient
      .get(this.url + '/getReminderDetails').pipe(catchError(this.handleError));
  }

  public getEmployees(searchFields?: ISearchField[], offset?: number, limit?: number, sort?: string): Observable<IUsers[]> {

    let params = ServiceUtils.buildQueryData(searchFields, offset, limit, sort);

    return this.http.get<IUsers[]>(this.url + '/getEmployees', { params }).pipe(map((response: any) => {
      return response;
    }), catchError(this.handleError));

  }

}
