package com.editor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.editor.utils.CookieUtil;
import com.editor.utils.GetMessage;
import com.editor.utils.VerificationUtil;
import com.github.qcloudsms.httpclient.HTTPException;

@Controller
@RequestMapping("/user")
@Api(tags="用户控制类")
public class UserController {

	@Autowired
	private GetMessage getMessage;
	@Autowired
	private VerificationUtil verificationUtil;
	@Autowired
	private CookieUtil cookieUtil;
	
	@PostMapping("/isCorrect")
	public void isCorrect(@RequestParam String username,HttpServletResponse response){
		try {
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
	
	/**
	 * 获取手机短信验证码
	 * @param tel 手机号码
	 */
	@PostMapping("/sendCode")
	@ApiOperation(value="注册页面获取手机短信验证码",notes="调用腾讯短信验证码API")
	@ApiImplicitParam(paramType="query",name="tel",value="手机号码",required=true,dataType="String")
	public void sendCode(@RequestParam String tel,HttpServletResponse response){
		String msg = null;
		try {
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
	
	/**
	 * 用户注册
	 * @param username 用户名
	 * @param password 密码
	 * @param tel 手机号码
	 * @param code 验证码
	 */
	@PostMapping("/registered")
	@ApiOperation(value="用户注册")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="username",value="用户名",required=true,dataType="String"),
		@ApiImplicitParam(paramType="query",name="password",value="密码",required=true,dataType="String"),
		@ApiImplicitParam(paramType="query",name="tel",value="手机号码",required=true,dataType="String"),
		@ApiImplicitParam(paramType="query",name="code",value="验证码",required=true,dataType="String"),
	})
	public void registered(@RequestParam String username,@RequestParam String password,@RequestParam String tel,@RequestParam String code,HttpServletResponse response){
		String msg = null;
		try {
			System.out.println(username+","+password+","+tel+","+code);
			cookieUtil.writeCookie(response, "username", username,1);
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
