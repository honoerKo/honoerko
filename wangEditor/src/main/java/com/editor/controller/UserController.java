package com.editor.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.editor.utils.CookieUtil;
import com.editor.utils.GetMessage;
import com.editor.utils.VerificationUtil;
import com.github.qcloudsms.httpclient.HTTPException;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private GetMessage getMessage;
	@Autowired
	private VerificationUtil verificationUtil;
	@Autowired
	private CookieUtil cookieUtil;
	
	@PostMapping("/isCorrect")
	public void isCorrect(HttpServletRequest request,HttpServletResponse response){
		try {
			String username = request.getParameter("username");
			System.out.println(username);
			String msg = "{\"msg\":1}";
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().print(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostMapping("/sendCode")
	public void sendCode(HttpServletRequest request,HttpServletResponse response){
		String msg = null;
		try {
			String tel = request.getParameter("tel");
			/*手机号码格式验证*/
			if (verificationUtil.isPhone(tel)) {
				/*判断该手机号码发送短信验证码是否在间隔时间内*/
				if(true){					
					/*调用短信API*/
					String result = getMessage.getCode(tel);
					if (result.length() == 6) {
						msg = "{\"msg\":\"发送成功\",\"value\":0}";
						/*将手机号码和验证码进行存储*/
						
					}else {
						msg = "{\"msg\":"+result+",\"value\":1}";
					}
				}/*else {
					
				}*/
			} else {
				msg = "{\"msg\":\"手机号码错误\",\"value\":1}";
			}			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().print(msg);
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
	
	@PostMapping("/registered")
	public void registered(HttpServletRequest request,HttpServletResponse response){
		String msg = null;
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String tel = request.getParameter("tel");
			String code = request.getParameter("code");
			System.out.println(username+","+password+","+tel+","+code);
			cookieUtil.writeCookie(response, "username", username);
			msg = "{\"msg\":\"注册成功\",\"value\":0}";
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().print(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
