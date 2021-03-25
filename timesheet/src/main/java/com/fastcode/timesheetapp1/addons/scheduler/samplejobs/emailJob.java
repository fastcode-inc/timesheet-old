package com.fastcode.timesheetapp1.addons.scheduler.samplejobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.fastcode.timesheetapp1.addons.email.application.mail.IEmailService;

public class emailJob implements Job {

	@Autowired
	private IEmailService _emailService;

	@Override
	public void execute(JobExecutionContext context) {

		String subject = "Reminder Email";
		String emailText = "This is a reminder email to fill out timesheet.\n";

		//JobKey key = context.getJobDetail().getKey();
        
		JobDataMap dataMap = context.getTrigger().getJobDataMap();
		String email = dataMap.getString("email");

		_emailService.sendEmail(_emailService.buildEmail(email, subject, emailText));

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
