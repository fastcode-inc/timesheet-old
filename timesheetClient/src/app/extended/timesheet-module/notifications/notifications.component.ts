import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { UsersExtendedService } from '../../admin/user-management/users';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class NotificationsComponent implements OnInit {
  title: string = 'Notifications';
  parentUrl: string = 'notification';

  loading = false;
  weekday = ['mon', 'tue', 'wed', 'thu', 'fri', 'sat','sun'];

  selectedDays = [];
  time: any;
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public usersService: UsersExtendedService,
  ) { }

  ngOnInit() {
    this.loading = true;
    this.usersService.getReminderDetails().subscribe(res => {
      this.loading = false;
      if(res) {
        this.selectedDays = res.days ? res.days.toLowerCase().split(',') : [];
        if(res.time) {
          let timeArr = res.time.split(':');
          let ampm = 'am';
          if(timeArr[0] >= 12) {
            ampm = 'pm';
            timeArr[0] -= 12;
          } else if (timeArr[0] == 0) {
            timeArr[0] = 12;
          }
          if(timeArr[1] < 10){
            timeArr[1] = '0' + timeArr[1];
          }
          this.time = `${timeArr[0]}:${timeArr[1]} ${ampm}`;
        }
      }
    })
  }

  isActive(day) {
    return this.selectedDays.includes(day);
  }

  selectDay(day) {
    if (this.selectedDays.includes(day)) {
      const index = this.selectedDays.indexOf(day);
      if (index > -1) {
        this.selectedDays.splice(index, 1);
      }
    } else {
      this.selectedDays.push(day);
    }
  }

  update() {
    this.loading = true;
    console.log(this.time)
    var data= {
      days: this.selectedDays.join(','),
      time: this.usersService.getFormattedTime(this.time, false).substring(0,5)
    }
    this.usersService.setReminder(data).subscribe(res => {
      this.loading = false;
    });
  }

  cancel() {}


}
