
import { NotificationsComponent } from 'src/app/extended/timesheet-module/notifications/notifications.component';
import { ManagerSheetComponent } from 'src/app/extended/timesheet-module/manager-sheet/manager-sheet.component';
import { TimesheetComponent } from 'src/app/extended/timesheet-module/timesheet/timesheet.component';
import { TimesheetDetailsComponent } from 'src/app/extended/timesheet-module/timesheet-details/timesheet-details.component';

import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { AuthGuard } from 'src/app/core/auth-guard';

const routes: Routes = [
    { path: "notifications", component: NotificationsComponent , canActivate: [ AuthGuard ] },
    { path: "managersheet", component: ManagerSheetComponent , canActivate: [ AuthGuard ] },
    { path: "timesheet", component: TimesheetComponent , canActivate: [ AuthGuard ] },
    { path: "timesheet-details", component: TimesheetDetailsComponent , canActivate: [ AuthGuard ] },    
];

export const timsheetRoute: ModuleWithProviders = RouterModule.forChild(routes);