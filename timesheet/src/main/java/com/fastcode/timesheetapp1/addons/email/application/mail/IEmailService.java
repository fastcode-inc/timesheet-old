package com.fastcode.timesheetapp1.addons.email.application.mail;

import org.springframework.mail.SimpleMailMessage;
import com.fastcode.timesheetapp1.addons.email.application.mail.dto.CreateEmailInput;

public interface IEmailService {

	void sendMessage(CreateEmailInput email);

	SimpleMailMessage buildEmail(String email, String subject, String emailText);
	
	void sendEmail(SimpleMailMessage email);
	
}

