package com.editor.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * Cookie
 * @author WF  2018年7月31日下午4:58:05
 *
 */
@Component
public class CookieUtil {

	/**
	 * 不设置时间Cookie只在一次回话有效
	 * @param response
	 * @param Key
	 * @param Value
	 * @throws UnsupportedEncodingException
	 */
	public void writeCookie(HttpServletResponse response,String key,String value) throws UnsupportedEncodingException{
		writeCookie(response, key, value, -1);
	}
	
	/**
	 * 写入时间限制的Cookie
	 * @param response
	 * @param Key
	 * @param Value
	 * @param expiry
	 * @throws UnsupportedEncodingException
	 */
	public void writeCookie(HttpServletResponse response,String key,String value,int expiry) throws UnsupportedEncodingException{
		Cookie newCookie;
		newCookie = new Cookie(key, URLEncoder.encode(value, "utf-8"));
		if(expiry > 0) expiry = expiry * 60 * 60;
		newCookie.setPath("/");
		newCookie.setMaxAge(expiry);
		response.addCookie(newCookie);
	}
	
	/**
	 * 读取Cookie
	 * @param request
	 * @param Key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String readCookie(HttpServletRequest request,String Key) throws UnsupportedEncodingException{
		String value = "";
		Cookie[] ck = request.getCookies();
		if(ck == null) return "";
		for (Cookie cookie : ck) {
			if (cookie.getName().equals(Key)) {
				value = cookie.getValue();
				break;
			}
		}
		return URLDecoder.decode(value, "utf-8");
	}
}
