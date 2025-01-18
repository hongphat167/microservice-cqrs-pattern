package com.hongphat.common_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * EmailService
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 11 :54 SA 12/01/2025
 */
@Configuration
public class EmailConfig {

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private int port;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	/**
	 * Java mail sender java mail sender.
	 *
	 * @return the java mail sender
	 */
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		// Thêm các cấu hình nâng cao
		props.put("mail.smtp.connectiontimeout", 5000);
		props.put("mail.smtp.timeout", 5000);
		props.put("mail.smtp.writetimeout", 5000);

		// Bật debug cho development
		props.put("mail.debug", true);

		return mailSender;
	}
}
