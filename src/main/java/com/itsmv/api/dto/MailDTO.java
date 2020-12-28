package com.itsmv.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDTO {
	private String address;
	private String title;
	private String message;

	public MailDTO(String address, String title, String message) {
		this.address = address;
		this.title = title;
		this.message = message;
	}
}
