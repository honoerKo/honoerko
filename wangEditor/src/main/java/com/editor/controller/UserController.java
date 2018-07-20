package com.editor.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.editor.utils.GetMessage;
import com.github.qcloudsms.httpclient.HTTPException;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private GetMessage getMessage;
	
	@PostMapping("/isCorrect")
	public void isCorrect(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String username = request.getParameter("username");
		System.out.println(username);
		String msg = "{\"msg\":1}";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(msg);
	}
	
	@PostMapping("/sendCode")
	public void sendCode(HttpServletRequest request,HttpServletResponse response){
		String msg = null;
		String tel = request.getParameter("tel");
		try {
			String result = getMessage.getCode(tel);
			if (result.length() == 6) {
				msg = "{\"msg\":\"发送成功\"}";
				System.out.println(result);
			}else {
				msg = "{\"msg\":"+result+"}";
				System.out.println(result);
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
	
	public static void main(String[] args) {

	}
}
