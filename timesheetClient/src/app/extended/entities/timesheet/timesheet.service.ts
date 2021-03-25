
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TimesheetService } from 'src/app/entities/timesheet/timesheet.service';
import { ITimesheetdetails } from 'src/app/entities/timesheetdetails';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ITimesheet } from 'src/app/entities/timesheet';
@Injectable({
  providedIn: 'root'
})
export class TimesheetExtendedService extends TimesheetService {
	constructor(protected httpclient: HttpClient) { 
    super(httpclient);
    this.url += '/extended';
  }
  
  /**
   * Calls api to create given item.
   * @param item
   * @returns Observable of created entity object.
   */
  createTimesheetDetails(items: ITimesheetdetails[]): Observable<any> {
    return this.http.post(this.url + '/timesheetdetails', items).pipe(catchError(this.handleError));
  }

  /**
   * Calls api to fetch list of timesheet details.
   * @param workDate
   * @returns Observable of created entity object.
   */
  getTimesheetDetails(workDate: Date): Observable<ITimesheetdetails[]> {
    let date = this.getFormattedDate(workDate)
    return this.http.get<ITimesheetdetails[]>(`${this.url}/timesheetdetails/?workDate=${date}`).pipe(catchError(this.handleError));
  }

  getByDate(workDate: Date, includeDetails?: boolean, userId?: number): Observable<any> {
    let params: any = { 
      date: this.getFormattedDate(workDate),
      includeDetails: includeDetails? 'true' : 'false'
    }
    if(userId) {
      params['userId'] = userId.toString();
    }
    return this.http.get(`${this.url}/getTimesheet`, {params: params}).pipe(catchError(this.handleError));
  }

  setTimesheetStatus(timesheetid:number, input) {
    return this.http.put(`${this.url}/${timesheetid}/updateTimesheetStatus`, input).pipe(catchError(this.handleError));
  }

  /**
   * Calls api to create given item.
   * @param item
   * @returns Observable of created entity object.
   */
  public create(item): Observable<any> {
    return this.http
      .post(this.url, item).pipe(catchError(this.handleError));
  }

  /**
   * returns formatted date string
   * dd-mm-yyyy
   * @param date 
   */
  getFormattedDate(date: Date, format?: string) : string {
    let day = date.getDate() > 9 ? date.getDate() : '0' + date.getDate();
    let m = date.getMonth() + 1;
    let mon = m > 9 ? m : '0' + m
    let year = date.getFullYear();
    if(format === 'yyyy-mm-dd') {
      return `${year}-${mon}-${day}`
    } else {
      return `${day}-${mon}-${year}`
    }
    
  }
}
