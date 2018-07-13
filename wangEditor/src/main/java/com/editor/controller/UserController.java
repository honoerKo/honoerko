package com.editor.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	@PostMapping("/isCorrect")
	public void isCorrect(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String username = request.getParameter("username");
		System.out.println(username);
		String msg = "{\"msg\":1}";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(msg);
	}
	
	public static void main(String[] args) {

	}
}
