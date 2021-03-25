import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import * as Moment from 'moment';
import { extendMoment } from 'moment-range';
import { TimesheetExtendedService } from '../../entities/timesheet';
import { ISearchCriteria, ISearchField, operatorType } from 'src/app/common/shared';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';
import { concat } from 'rxjs';

const moment = extendMoment(Moment);
@Component({
  selector: 'app-manager-sheet',
  templateUrl: './manager-sheet.component.html',
  styleUrls: ['./manager-sheet.component.scss'],
})
export class ManagerSheetComponent implements OnInit {
  title = 'Review Timesheet';
  loading = false;

  timesheetDate: Date;
  timesheetday;
  timesheettilldate;
  timesheets: any = [];
  
  public selection = new SelectionModel<any>(true, []);
  dataSource = new MatTableDataSource<any>();

  columnsToDisplay = ['select', 'employees', 'hours', 'actions']

 constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public timesheetService: TimesheetExtendedService,
	) { }

	ngOnInit() {
    this.timesheetDate = new Date();
    this.setTimesheet('current');
  }

  getTimeSheets() {
    this.clearSelection();
    let filters: ISearchField[] = [
      {
        operator: operatorType.Equals,
        fieldName: 'periodstartingdate',
        searchValue: this.timesheetService.getFormattedDate(this.timesheetDate, 'yyyy-mm-dd')
      },
      {
        operator: operatorType.Equals,
        fieldName: 'timesheetstatus',
        searchValue: 'Submitted'
      },
    ];
    this.timesheetService.getAll(filters).subscribe(res => {
      this.timesheets = res;
      this.dataSource.data = res;
    })
  }

  changeTimeSheetStatus(timesheet, userid: number, status: string) {
    let input: any = {
      status: status,
      userId: userid
    }
    this.loading = true;
    this.timesheetService.setTimesheetStatus(timesheet.id, input).subscribe(res => {
      this.loading = false;
      this.getTimeSheets();
    });
  }

  changeSelectedTimeSheetStatus(status: string) {
    this.loading = true;
    const observables = [];
    this.selection.selected.forEach(timesheet => {
      let input: any = {
        status: status,
        userId: timesheet.userid
      }
      observables.push(this.timesheetService.setTimesheetStatus(timesheet.id, input));
    });
    concat(...observables).subscribe(res => {
      this.loading = false;
      this.getTimeSheets();
    });
  }

  dateChanged(event) {
    this.setTimesheet('current');
  }

  setTimesheet(state) {
    var date = new Date(this.timesheetDate);
    var day = date.getDate();
    var nextDay = new Date( this.timesheetDate);

    var firstDate = new Date (this.timesheetDate);
    var lastDate = new Date (this.timesheetDate);
    if (state == 'current') {
      if (day <= 15 ) {
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        this.timesheetDate = firstDay;
        lastDate.setDate(new Date(firstDay).getDate() + 14);
        this.timesheettilldate = lastDate;
      } else {
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        firstDate.setDate(firstDay.getDate() + 15);
        this.timesheetDate = firstDate;
        this.timesheettilldate = lastDay;
      }
    } else if (state == 'next') {
      if (day <= 15 ) {
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        firstDate.setDate(firstDay.getDate() + 15);
        this.timesheetDate = firstDate;
        this.timesheettilldate = lastDay;

      } else {
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        firstDate.setDate(new Date(lastDay).getDate() + 1);
        this.timesheetDate = firstDate;
        lastDate.setDate(new Date(lastDay).getDate() + 15);
        this.timesheettilldate = lastDate;

      }
    } else {
        console.log('back');
        if (day <= 15 ) {
          var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
          var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
          lastDate.setDate(firstDay.getDate() - 1 );
          this.timesheettilldate = lastDate;
          var firstDayofLM = new Date(lastDate.getFullYear(), lastDate.getMonth(), 1);
          var fd = new Date(lastDate);
          fd.setDate(firstDayofLM.getDate() + 15);
          this.timesheetDate = fd;

        } else {
          var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
          var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);

          this.timesheetDate = firstDay;
          lastDate.setDate(firstDay.getDate() + 14);
          this.timesheettilldate = lastDate;

        }
    }

    this.getTimeSheets();

  }

  masterToggle() {
    this.selection.hasValue() ?
      this.clearSelection() :
      this.dataSource.data.forEach(row => {
        this.selection.select(row);
      });
  }

  selectTimesheet(row, event) {
    if(event) {
      this.selection.toggle(row);
    }
  }

  selectAll(event) {
    if(event) {
      this.masterToggle();
    }
  }

  clearSelection() {
    this.selection.clear();
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

}
