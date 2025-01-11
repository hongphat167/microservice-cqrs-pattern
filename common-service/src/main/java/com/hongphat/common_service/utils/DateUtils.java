package com.hongphat.common_service.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * DateUtils
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 12 :21 SA 11/01/2025
 */
public class DateUtils {
	/**
	 * Convert Date to LocalDateTime
	 *
	 * @param date Date to convert
	 * @return LocalDateTime local date time
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		if (date == null) {
			return null;
		}
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Convert LocalDateTime to Date
	 *
	 * @param localDateTime LocalDateTime to convert
	 * @return Date date
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return null;
		}
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Check if date is before current date
	 *
	 * @param date Date to check
	 * @return boolean boolean
	 */
	public static boolean isBeforeNow(Date date) {
		if (date == null) {
			return false;
		}
		return date.before(new Date());
	}

	/**
	 * Check if localDateTime is before current date
	 *
	 * @param localDateTime LocalDateTime to check
	 * @return boolean boolean
	 */
	public static boolean isBeforeNow(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return false;
		}
		return localDateTime.isBefore(LocalDateTime.now());
	}
}
