package com.editor.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.springframework.stereotype.Component;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

/**
 * 发送验证码短信
 * @author WF  2018年7月13日下午3:52:19
 *
 */
@Component
public class GetMessage {
	
    /**
     * @param tel
     * @return 调用短信验证平台API，返回6位验证码或者错误信息
     * @throws JSONException
     * @throws HTTPException
     * @throws IOException
     */
    public String getCode(String tel,String randNum) throws JSONException, HTTPException, IOException {
    	SmsSingleSender sender = new SmsSingleSender(ConstantUtil.ACCOUNT_SID, ConstantUtil.AUTH_TOKEN);
        ArrayList<String> params = new ArrayList<String>();
        params.add(randNum);
        params.add("5");
        SmsSingleSenderResult result = sender.sendWithParam("86",tel , ConstantUtil.TEMPLATE_ID, params, "", "", "");
        if (result.result == 0) {
        	return randNum;
		}else {
			return result.result+"";
		}       
    }
}
