package com.doms.api.svc;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.doms.api.dto.MailDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailSvc {
	private JavaMailSender mailSender;
	private static final String FROM_ADDRESS = "ITSM-View@torayamk.com";

	public void mailSend(MailDTO mailDTO) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(FROM_ADDRESS);
		message.setTo(mailDTO.getAddress());
		message.setSubject(mailDTO.getTitle());
		message.setText(mailDTO.getMessage());

		mailSender.send(message);
	}
}
