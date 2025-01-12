package com.hongphat.common_service.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

/**
 * EmailService
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 11 :30 SA 12/01/2025
 */
@Service
@Slf4j
public class EmailService {

	private final JavaMailSender mailSender;

	/**
	 * Instantiates a new Email service.
	 *
	 * @param mailSender the mail sender
	 */
	protected EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}


	/**
	 * Send email.
	 *
	 * @param to      the to
	 * @param subject the subject
	 * @param content the content
	 * @param isHtml  the is html
	 * @param file    the file
	 */
	public void sendEmail(String to, String subject, String content, boolean isHtml, File file) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(content, isHtml);

			// Add attachment if provided
			if (file != null) {
				FileSystemResource fileSystemResource = new FileSystemResource(file);
				mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()), fileSystemResource);
			}

			mailSender.send(mimeMessage);
			log.info("Email sent successfully to {}", to);
		} catch (MessagingException e) {
			log.error("Failed to send email to {}", to, e);
		}
	}

}
