package com.hongphat.common_service.utils;

import com.google.common.base.Strings;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * StringUtils
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 12 :21 SA 11/01/2025
 */
public class StringUtils {
	/**
	 * Check if string is null or empty
	 *
	 * @param str String to check
	 * @return boolean boolean
	 */
	public static boolean isNullOrEmpty(String str) {
		return Strings.isNullOrEmpty(str);
	}

	/**
	 * Convert string to slug
	 * Example: "Hello World" -> "hello-world"
	 *
	 * @param input String to convert
	 * @return String string
	 */
	public static String toSlug(String input) {
		if (isNullOrEmpty(input)) {
			return "";
		}

		// Convert to lowercase and normalize
		String normalized = Normalizer.normalize(input.toLowerCase(), Normalizer.Form.NFD);

		// Remove diacritics
		Pattern pattern = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
		String ascii = pattern.matcher(normalized).replaceAll("");

		// Replace special characters with hyphen
		ascii = ascii.replaceAll("[^a-zA-Z0-9\\s]", "");

		// Replace spaces with hyphen
		ascii = ascii.replaceAll("\\s+", "-");

		// Remove leading/trailing hyphens
		ascii = ascii.replaceAll("^-+|-+$", "");

		return ascii;
	}

	/**
	 * Capitalize first letter of each word
	 * Example: "hello world" -> "Hello World"
	 *
	 * @param input String to convert
	 * @return String string
	 */
	public static String capitalizeWords(String input) {
		if (isNullOrEmpty(input)) {
			return "";
		}

		String[] words = input.split("\\s");
		StringBuilder result = new StringBuilder();

		for (String word : words) {
			if (!word.isEmpty()) {
				result.append(Character.toUpperCase(word.charAt(0)))
						.append(word.substring(1).toLowerCase())
						.append(" ");
			}
		}

		return result.toString().trim();
	}

	/**
	 * Remove all special characters from string
	 *
	 * @param input String to clean
	 * @return String string
	 */
	public static String removeSpecialCharacters(String input) {
		if (isNullOrEmpty(input)) {
			return "";
		}
		return input.replaceAll("[^a-zA-Z0-9\\s]", "");
	}

	/**
	 * Mask string with asterisks
	 * Example: "1234567890" -> "******7890"
	 *
	 * @param input        String to mask
	 * @param visibleChars Number of characters to show at end
	 * @return String string
	 */
	public static String mask(String input, int visibleChars) {
		if (isNullOrEmpty(input) || visibleChars < 0) {
			return input;
		}

		int length = input.length();
		if (length <= visibleChars) {
			return input;
		}

		String asterisks = "*".repeat(length - visibleChars);
		return asterisks + input.substring(length - visibleChars);
	}
}
