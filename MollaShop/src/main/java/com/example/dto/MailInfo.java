package com.example.dto;

import com.example.common.MailType;

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
	private MailType mailType;

	public MailInfo(String to, String subject, String body, MailType mailType) {
		this.from = "Molla Shop online <MollaShop@gmail.com>";
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.mailType = mailType;
	}
}
