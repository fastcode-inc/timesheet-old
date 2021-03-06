import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute,Router} from "@angular/router";
import { FormBuilder, Validators} from '@angular/forms';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { AppConfigurationService } from '../app-configuration.service';
import { IAppConfiguration } from '../iapp-configuration';
import { Globals, BaseNewComponent, PickerDialogService, ErrorService } from 'src/app/common/shared';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';


@Component({
  selector: 'app-app-configuration-new',
  templateUrl: './app-configuration-new.component.html',
  styleUrls: ['./app-configuration-new.component.scss']
})
export class AppConfigurationNewComponent extends BaseNewComponent<IAppConfiguration> implements OnInit {
  
    title:string = "New AppConfiguration";
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public dialogRef: MatDialogRef<AppConfigurationNewComponent>,
		@Inject(MAT_DIALOG_DATA) public data: any,
		public global: Globals,
		public pickerDialogService: PickerDialogService,
		public appConfigurationService: AppConfigurationService,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) {
		super(formBuilder, router, route, dialog, dialogRef, data, global, pickerDialogService, appConfigurationService, errorService);
	}
 
	ngOnInit() {
		this.entityName = 'AppConfiguration';
		this.setAssociations();
		super.ngOnInit();
    	this.setForm();
		this.checkPassedData();
    }
 		
	setForm(){
 		this.itemForm = this.formBuilder.group({
      type: ['', Validators.required],
      value: ['', Validators.required],
    });
    
    this.fields = [
      {
		name: 'type',
		label: 'type',
		isRequired: true,
		isAutoGenerated: false,
	      type: 'string',
	    },
      {
		name: 'value',
		label: 'value',
		isRequired: true,
		isAutoGenerated: false,
	      type: 'string',
	    },
		];
	}
	 
	setAssociations(){
  	
		this.associations = [
		];
		this.parentAssociations = this.associations.filter(association => {
			return (!association.isParent);
		});

	}
	
	onSubmit() {
		let appConfiguration = this.itemForm.getRawValue();
		super.onSubmit(appConfiguration);
		
	}
    
}
