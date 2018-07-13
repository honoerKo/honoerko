package com.editor.utils;

import java.io.IOException;

import org.json.JSONException;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

/**
 * 发送验证码短信
 * @author WF  2018年7月13日下午3:52:19
 *
 */
public class GetMessage {

	public static String[] randNum = {RandUtil.getRandNum()};
	
    public static void getResult(String tel) {
		try {
			SmsSingleSender ssender = new SmsSingleSender(ConstantUtil.ACCOUNT_SID, ConstantUtil.AUTH_TOKEN);
			SmsSingleSenderResult result = ssender.sendWithParam("86", tel, ConstantUtil.TEMPLATE_ID, randNum, ConstantUtil.SMS_SIGN, "", "");
			System.out.print(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HTTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
		//System.out.println(randNum[0]);
	}
}
