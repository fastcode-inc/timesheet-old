import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import * as Moment from 'moment';
import { extendMoment } from 'moment-range';
import { TimesheetExtendedService } from 'src/app/extended/entities/timesheet';

const moment = extendMoment(Moment);
@Component({
  selector: 'app-timesheet-details',
  templateUrl: './timesheet-details.component.html',
  styleUrls: ['./timesheet-details.component.scss'],
})
export class TimesheetDetailsComponent implements OnInit {
  title = 'Timesheet Details';
  parentUrl = 'timesheet-details';
  loading = false;

  weekday = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
  Months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  month: any;

  notes: any;
  totalhours: any = 0;
  timesheetDate: any;
  timesheetday;
  timesheettilldate;
  dayList = [];
  customerProjects = [];
  customers = [];
  projects = [];
  userid: any;
  columnWiseSumMap = {};
  rowWiseSumMap = {};
  totalSum = 0;

  timesheet;

  timesheetDetails = [];
  uniqueTasks = [];
  timesheetDetailsMap = {};
  editable = false;
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public timesheetService: TimesheetExtendedService,
  ) { }

  ngOnInit() {
    this.userid = this.route.snapshot.queryParamMap.get('userid');
    if (this.userid && this.route.snapshot.queryParamMap.get('timesheetDate')) {
      this.timesheetDate = new Date(this.route.snapshot.queryParamMap.get('timesheetDate'));
    } else {
      this.timesheetDate = new Date();
    }

    this.setTimesheet('current');
  }

  getTimesheet(step: string) {
    this.resetData();
    if (this.userid) {
      this.timesheetService.getByDate(this.timesheetDate, true, this.userid).subscribe(res => {
        this.timesheet = res;
        this.timesheetDetails = this.timesheet.timesheetdetailsList;
        this.notes = this.timesheet.notes;
        this.setTimesheetDetailsData();
      }, err => this.setTimesheetDetailsData());
    } else {
      this.timesheetService.getByDate(this.timesheetDate, true).subscribe(res => {
        this.timesheet = res;
        this.timesheetDetails = this.timesheet.timesheetdetailsList;
        this.notes = this.timesheet.notes;
        this.setTimesheetDetailsData();
      }, err => this.setTimesheetDetailsData());
    }
  }

  setTimesheetDetailsData() {
    this.editable = 
      ( !this.userid && this.timesheet &&
        ( this.timesheet.timesheetstatusDescriptiveField === 'Open' ||
          this.timesheet.timesheetstatusDescriptiveField === 'Rejected'
        )
      ) ||
      (
        this.userid && this.timesheet && this.timesheet.timesheetstatusDescriptiveField === 'Submitted'
      );

    this.timesheetDetailsMap = this.timesheetDetails.reduce((r, timesheetobj) => {
      //for getting unique tasks
      var i = this.uniqueTasks.findIndex(task => {
        if(task.taskid) {
          return timesheetobj.taskid === task.taskid
        } else {
          return task.timeofftypeid === timesheetobj.timeofftypeid;
        }
      });
      if(i <= -1){
        this.uniqueTasks.push(timesheetobj);
      }
      // Managing row and column wise sum
      let currSum = this.columnWiseSumMap[timesheetobj.workdate];
      this.columnWiseSumMap[timesheetobj.workdate] = (currSum || 0) + timesheetobj.hours;
      currSum = this.rowWiseSumMap[(timesheetobj.taskid || ('to-' + timesheetobj.timeofftypeid))];
      this.rowWiseSumMap[(timesheetobj.taskid || ('to-' + timesheetobj.timeofftypeid))] = (currSum || 0) + timesheetobj.hours;
      this.totalSum += timesheetobj.hours;
      // constructing timesheetdetails map
      r[timesheetobj.workdate + '-' + (timesheetobj.taskid || ('to-' + timesheetobj.timeofftypeid))] = timesheetobj || {};
      return r;
    }, Object.create(null));
    console.log(this.timesheetDetailsMap)
  }

  resetData() {
    this.rowWiseSumMap = {};
    this.columnWiseSumMap = {};
    this.totalSum = 0;
    this.uniqueTasks = [];
    this.timesheetDetails = [];
  }

  dateChanged(event) {
    this.setTimesheet('current');
  }

  setTimesheet(state) {
    this.dateList = [];
    var date = new Date(this.timesheetDate);
    var day = date.getDate();
    var nextDay = new Date(this.timesheetDate);

    var firstDate = new Date(this.timesheetDate);
    var lastDate = new Date(this.timesheetDate);
    if (state == 'current') {
      if (day <= 15) {
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);

        this.timesheetDate = firstDay;
        lastDate.setDate(new Date(firstDay).getDate() + 14);
        this.timesheettilldate = lastDate;
        this.getdateList(this.timesheetDate, lastDate);
      } else {
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        firstDate.setDate(firstDay.getDate() + 15);
        this.timesheetDate = firstDate;
        this.timesheettilldate = lastDay;
        this.getdateList(this.timesheetDate, this.timesheettilldate);
      }
    } else if (state == 'next') {
      if (day <= 15) {
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);

        firstDate.setDate(firstDay.getDate() + 15);
        this.timesheetDate = firstDate;
        this.timesheettilldate = lastDay;
        this.getdateList(this.timesheetDate, lastDay);
      } else {
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        firstDate.setDate(new Date(lastDay).getDate() + 1);
        this.timesheetDate = firstDate;
        lastDate.setDate(new Date(lastDay).getDate() + 15);
        this.timesheettilldate = lastDate;
        this.getdateList(this.timesheetDate, this.timesheettilldate);
      }
    } else {
      if (day <= 15) {
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        lastDate.setDate(firstDay.getDate() - 1);
        this.timesheettilldate = lastDate;
        var firstDayofLM = new Date(lastDate.getFullYear(), lastDate.getMonth(), 1);
        var fd = new Date(lastDate);
        fd.setDate(firstDayofLM.getDate() + 15);
        this.timesheetDate = fd;

        this.getdateList(this.timesheetDate, this.timesheettilldate);
      } else {
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);

        this.timesheetDate = firstDay;
        lastDate.setDate(firstDay.getDate() + 14);
        this.timesheettilldate = lastDate;
        this.getdateList(this.timesheetDate, this.timesheettilldate);
      }
    }

    this.getTimesheet(state);

  }

  dateList: string[] = [];
  getdateList(startdate, enddate) {
    this.dayList = [];
    const start = new Date(startdate), end = new Date(enddate);
    const range = moment.range(moment(start), moment(end));
    var dateList = Array.from(range.by('day'));
    var mon = moment(dateList[0]).month();
    this.month = this.Months[mon];
    for (var i = 0; i < dateList.length; i++) {
      var day = moment(dateList[i]).day();
      var date = moment(dateList[i]).date();
      this.dayList.push(this.weekday[day] + ' ' + date);
    }
    this.dateList = dateList.map(d => this.timesheetService.getFormattedDate(d.toDate(), 'yyyy-mm-dd'));
  }

  changeTimeSheetStatus(status: string) {
    this.loading = true;
    let input: any = {
      status: status,
      notes: this.notes
    }
    if(this.userid) {
      input.userId = this.userid;
      this.timesheetService.setTimesheetStatus(this.timesheet.id, input).subscribe(res => {
        this.setTimesheet('current');
        this.loading = false;
      });
    } else {
      this.timesheetService.setTimesheetStatus(this.timesheet.id, input).subscribe(res => {
        this.setTimesheet('current');
        this.loading = false;
      });
    }
  }
}
