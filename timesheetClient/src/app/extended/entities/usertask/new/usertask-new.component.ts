import { Component, OnInit, Inject } from '@angular/core';
import { UsertaskExtendedService } from '../usertask.service';

import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder, Validators } from '@angular/forms';
import { Globals, PickerDialogService, ErrorService, IAssociationEntry, IFCDialogConfig, ISearchField, operatorType } from 'src/app/common/shared';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { TaskExtendedService } from 'src/app/extended/entities/task/task.service';
import { UsersExtendedService } from 'src/app/extended/admin/user-management/users/users.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

import { IUsertask, UsertaskNewComponent } from 'src/app/entities/usertask';
import { ITask } from 'src/app/entities/task';
import { first } from 'rxjs/operators';
import { forkJoin, observable } from 'rxjs';

@Component({
  selector: 'app-usertask-new',
  templateUrl: './usertask-new.component.html',
  styleUrls: ['./usertask-new.component.scss']
})
export class UsertaskNewExtendedComponent extends UsertaskNewComponent implements OnInit {

  title: string = "New Usertask";
  selectedTasks: ITask[] = [];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<UsertaskNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public global: Globals,
    public pickerDialogService: PickerDialogService,
    public usertaskExtendedService: UsertaskExtendedService,
    public errorService: ErrorService,
    public taskExtendedService: TaskExtendedService,
    public usersExtendedService: UsersExtendedService,
    public globalPermissionService: GlobalPermissionService,
  ) {
    super(formBuilder, router, route, dialog, dialogRef, data, global, pickerDialogService, usertaskExtendedService, errorService,
      taskExtendedService,
      usersExtendedService,
      globalPermissionService,
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  setForm() {
    this.itemForm = this.formBuilder.group({
      userid: ['', Validators.required],
      taskDescriptiveField: [''],
      usersDescriptiveField: [''],
    });
  }

  selectAssociation(association: IAssociationEntry) {
    let dialogConfig: IFCDialogConfig = <IFCDialogConfig>{
      Title: association.table,
      IsSingleSelection: true,
      DisplayField: association.referencedDescriptiveField
    };
    if (association.table === 'task') {
      dialogConfig.IsSingleSelection = false;
    }

    this.pickerDialogRef = this.pickerDialogService.open(dialogConfig);

    this.initializePickerPageInfo();
    if (association.table === 'users') {
      this.usersExtendedService.getEmployees(this.searchValuePicker, this.currentPickerPage * this.pickerPageSize, this.pickerPageSize).subscribe(items => {
        this.isLoadingPickerResults = false;
        this.pickerDialogRef.componentInstance.items = items;
        association.data = items;
        this.updatePickerPageInfo(items);
      }, (error) => {
        this.errorMessage = <any>error;
        this.errorService.showError(this.errorMessage);
      }); 
    } else {
      association.service.getAll(this.searchValuePicker, this.currentPickerPage * this.pickerPageSize, this.pickerPageSize).subscribe(items => {
        this.isLoadingPickerResults = false;
        this.pickerDialogRef.componentInstance.items = items;
        association.data = items;
        this.updatePickerPageInfo(items);
      }, (error) => {
        this.errorMessage = <any>error;
        this.errorService.showError(this.errorMessage);
      }
      ); 
    }

    this.pickerDialogRef.componentInstance.onScroll.subscribe(data => {
      this.onPickerScroll(association);
    })

    this.pickerDialogRef.componentInstance.onSearch.subscribe(data => {
      this.onPickerSearch(data, association,);
    })

    this.pickerDialogRef.afterClosed().subscribe(associatedItem => {

      if (association.table === 'task') {
        if(associatedItem){
          this.selectedTasks = associatedItem;
          this.itemForm.get(association.descriptiveField).setValue(
            Array.prototype.map.call(associatedItem, function (item) { return item[association.referencedDescriptiveField]; }).join(","));
        }
      } else {
        this.onPickerClose(associatedItem, association);
      }
    });
  }

  onSubmit() {
    // stop here if form is invalid
    if (this.itemForm.invalid) {
      return;
    }

    this.submitted = true;
    this.loading = true;
    let observables = [];
    this.selectedTasks.forEach(task => {
      let data: IUsertask = {
        taskid: task.id,
        userid: this.itemForm.value.userid
      }
      observables.push(this.dataService.create(data));
    });

    forkJoin(observables).subscribe(
      (data) => {
        this.dialogRef.close(data);
      },
      (error) => {
        this.errorService.showError('Error Occured while creating');
        this.loading = false;
        this.dialogRef.close(null);
      }
    );
  }
}
