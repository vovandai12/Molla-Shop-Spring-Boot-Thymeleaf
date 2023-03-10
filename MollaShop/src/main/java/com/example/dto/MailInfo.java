package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
	private String from;
	private String to;
	private String[] cc;
	private String[] bcc;
	private String subject;
	private String body;
	private String[] attachments;
	private String mailType;

	public MailInfo(String to, String subject, String body, String mailType) {
		this.from = "Molla Shop online <MollaShop@gmail.com>";
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.mailType = mailType;
	}
}
