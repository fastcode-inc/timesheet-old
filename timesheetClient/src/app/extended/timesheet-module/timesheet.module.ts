import { NgModule } from '@angular/core';

import { NotificationsComponent } from 'src/app/extended/timesheet-module/notifications/notifications.component';
import { ManagerSheetComponent } from 'src/app/extended/timesheet-module/manager-sheet/manager-sheet.component';
import { TimesheetComponent } from 'src/app/extended/timesheet-module/timesheet/timesheet.component';
import { TimesheetDetailsComponent } from 'src/app/extended/timesheet-module/timesheet-details/timesheet-details.component';

import { timsheetRoute } from './timesheet.routing';

import { SharedModule  } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/common/general-components/extended/general-extended.module';

const entities = [
    NotificationsComponent,
    TimesheetComponent,
    TimesheetDetailsComponent,
    ManagerSheetComponent 
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    timsheetRoute,
    SharedModule,
    GeneralComponentsExtendedModule,
  ]
})
export class TimesheetModule {
}
