package com.editor.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/**
 * 校验工具
 * @author WF  2018年7月31日下午4:49:37
 *
 */
@Component
public class VerificationUtil {

	/**
     * @param tel
     * @return 判断是否为正确的手机号码
     */
    public Boolean isPhone(String tel){
    	String regex = "^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(166)|(17[0-9])|(18[0-9])|(19[8|9]))\\d{8}$";
    	if (tel.length() != 11) {
			return false;
		}else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(tel);
			return m.matches();
		}
    }
}
