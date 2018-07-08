/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：Md5Util.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * The class Md 5 util.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Md5Util {

	/**
	 * Encrypt string.
	 *
	 * @param password 密码
	 *
	 * @return the string
	 */
	public static String encrypt(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

	/**
	 * 密码是否匹配.
	 *
	 * @param rawPassword     明文
	 * @param encodedPassword 密文
	 *
	 * @return the boolean
	 */
	public static boolean matches(CharSequence rawPassword, String encodedPassword) {
		return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
	}

	public static String stringMD5(String input) {

		try {

			// 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）

			MessageDigest messageDigest =MessageDigest.getInstance("MD5");


			// 输入的字符串转换成字节数组

			byte[] inputByteArray = input.getBytes();



			// inputByteArray是输入字符串转换得到的字节数组

			messageDigest.update(inputByteArray);



			// 转换并返回结果，也是字节数组，包含16个元素

			byte[] resultByteArray = messageDigest.digest();



			// 字符数组转换成字符串返回

			return byteArrayToHex(resultByteArray);



		} catch (NoSuchAlgorithmException e) {

			return null;

		}

	}

	private static String byteArrayToHex(byte[] byteArray) {

		// 首先初始化一个字符数组，用来存放每个16进制字符

		char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））

		char[] resultCharArray = new char[byteArray.length * 2];


		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

		int index = 0;

		for (byte b : byteArray) {

			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];

			resultCharArray[index++] = hexDigits[b & 0xf];

		}


		// 字符数组组合成字符串返回

		return new String(resultCharArray);
	}

	public static void main(String[] args) {
		String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIioiXSwibG9naW5OYW1lIjoiYWRtaW4iLCJleHAiOjE1MzA5ODU1NTEsImF1dGhvcml0aWVzIjpbIi9jYXJ0L2RlbGV0ZVByb2R1Y3QvKiIsIi9tZW51L3NhdmUiLCIvcm9sZS9iaW5kQWN0aW9uIiwiL2FjdGlvbi9kZWxldGVBY3Rpb25CeUlkLyoiLCIvbWVudS9tb2RpZnlTdGF0dXMiLCIvb21jL3Byb2R1Y3QvZGVsZXRlUHJvZHVjdEJ5SWQvKiIsIi9yb2xlL2RlbGV0ZVJvbGVCeUlkLyoiLCIvb21jL2NhdGVnb3J5L2RlbGV0ZUJ5SWQvKiIsIi9kaWN0L21vZGlmeVN0YXR1cyIsIi9vcmRlci9jcmVhdGVPcmRlckRvYy8qIiwiL2VtYWlsL3NlbmRSZXN0RW1haWxDb2RlIiwiL21lbnUvZGVsZXRlQnlJZC8qIiwiL2dyb3VwL2RlbGV0ZUJ5SWQvKiIsIi91c2VyL2JpbmRSb2xlIiwiL3NoaXBwaW5nL3NldERlZmF1bHRBZGRyZXNzLyoiLCIvYWN0aW9uL21vZGlmeVN0YXR1cyIsIi9ncm91cC9zYXZlIiwiL2dyb3VwL2JpbmRVc2VyIiwiL2RpY3Qvc2F2ZSIsIi9hY3Rpb24vY2hlY2tVcmwiLCIvYWN0aW9uL2JhdGNoRGVsZXRlQnlJZExpc3QiLCIvY2FydC9zZWxlY3RBbGxQcm9kdWN0IiwiL2FjdGlvbi9jaGVja0FjdGlvbkNvZGUiLCIvb3JkZXIvY2FuY2VsT3JkZXJEb2MvKiIsIi9yb2xlL21vZGlmeVJvbGVTdGF0dXNCeUlkIiwiL3NoaXBwaW5nL2RlbGV0ZVNoaXBwaW5nLyoiLCIvY2FydC91blNlbGVjdFByb2R1Y3QvKiIsIi9zaGlwcGluZy91cGRhdGVTaGlwcGluZy8qIiwiL2dyb3VwL21vZGlmeVN0YXR1cyIsIi9yb2xlL2JpbmRVc2VyIiwiL3VhYy9yb2xlL3F1ZXJ5TGlzdCIsIi9vbWMvcHJvZHVjdC9zYXZlIiwiL3BheS9hbGlwYXlDYWxsYmFjayIsIi9vbWMvY2F0ZWdvcnkvbW9kaWZ5U3RhdHVzIiwiL2NhcnQvdXBkYXRlSW5mb3JtYXRpb24iLCIvY2FydC91blNlbGVjdEFsbFByb2R1Y3QiLCIvZGljdC9kZWxldGVCeUlkLyoiLCIvdXNlci9zYXZlIiwiL2NhcnQvdXBkYXRlUHJvZHVjdC8qKiIsIi91c2VyL3Jlc2V0TG9naW5Qd2QiLCIvcGF5L2NyZWF0ZVFyQ29kZUltYWdlLyoiLCIvYWN0aW9uL3F1ZXJ5TGlzdFdpdGhQYWdlIiwiL2NhcnQvc2VsZWN0UHJvZHVjdC8qIiwiL2NhcnQvYWRkUHJvZHVjdC8qKiIsIi9yb2xlL3NhdmUiLCIvYWN0aW9uL3NhdmUiLCIvdXNlci9tb2RpZnlVc2VyU3RhdHVzQnlJZCIsIi9zaGlwcGluZy9hZGRTaGlwcGluZyIsIi9vbWMvY2F0ZWdvcnkvc2F2ZSIsIi9yb2xlL2JpbmRNZW51IiwiL3JvbGUvYmF0Y2hEZWxldGVCeUlkTGlzdCJdLCJqdGkiOiI4NTA3ZDQxZS0zYmNlLTQwMDMtYjFhOC03MjQwMzFlYjM2Y2EiLCJjbGllbnRfaWQiOiJwYWFzY2xvdWQtY2xpZW50LXVhYyIsInRpbWVzdGFtcCI6MTUzMDk3ODM1MTI2OH0.OT34EguC4eLDmUcsXMPF5N7wH0vXoEKaLMWm9rqzBH0";
		System.out.println(stringMD5(token));
		System.out.println(stringMD5(token));
		System.out.println(stringMD5(token));
		System.out.println(stringMD5(token));
		System.out.println(stringMD5(token));
		System.out.println(stringMD5(token));
	}

}
