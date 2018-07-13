package com.editor.utils;

import java.util.Random;

/**
 * 生成随机字符类
 * @author WF  2018年7月13日下午3:48:34
 *
 */
public class RandUtil {

	/**
	 * 6位随机数字字符串
	 * @return
	 */
	public static String getRandNum(){
		String randNum = new Random().nextInt(1000000)+"";
        //System.out.println("生成"+randNum);
        if (randNum.length()!=6) {   //如果生成的不是6位数随机数则返回该方法继续生成
            return getRandNum();
        }
        return randNum;
	}
}
