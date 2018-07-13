package com.editor.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.editor.service.IFileUploadService;
import com.editor.utils.ImgUploadUtil;
import com.editor.model.FileUpload;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/we")
public class WangEditorController {

	@Resource
	private IFileUploadService fileService;
	@Autowired
	private ImgUploadUtil imgUpload;
	
	@RequestMapping("/editor")
	public String editor(){
		return "page/editor";
	}
	@RequestMapping("/detail")
	public String detail(){
		return "page/detail";
	}
	
	/**
	 * 将前端返回的编辑器数据存储进数据库并返回提示信息
	 * @param reques
	 * @param response
	 * @throws IOException
	 */
	@PostMapping("/saveOrReplace")
	public void saveOrReplace(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String msg = null;
		String fid = request.getParameter("fid");
		String title = request.getParameter("title");
		String detail = request.getParameter("detail");
		//去除前端编辑时不合法的换行符
		detail = detail.replaceAll("\n", "\\\\n");
		FileUpload file = new FileUpload();
		file.setTitle(title);
		file.setDetail(detail);		
		int i = 0;
		if("".equals(fid) || fid == null){
			file.setCode((short) 1);
			i = fileService.insert(file);
		}else{
			Short id = Short.parseShort(fid);
			file.setFid(id);
			i = fileService.updateByPrimaryKeyWithBLOBs(file);
		}
		if(i != 0){
			msg = "{\"msg\":\"发布成功!\"}";
		}else {
			msg = "{\"msg\":\"发布失败!\"}";
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(msg);
	}
	
	/**
	 * 查询最后一次发布的一条数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@PostMapping("/queryByMaxId")
	public void queryByMaxId(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String msg = null;
		try {
			FileUpload fuList = fileService.selectByMaxId();
			if(fuList != null) {
				String detail = fuList.getDetail();
				String title = fuList.getTitle();
				//需将数据中的双引号添加转义符才能以JSON格式传递到前端显示
				detail = detail.replaceAll("\"", "\\\\\"");
				msg = "{\"msg\":\""+detail+"\",\"title\":\""+title+"\",\"value\":1}";
			}else {
				msg = "{\"msg\":\"查询结果为空\"}";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg = "{\"msg\":\"获取失败！\",\"value\":0}";
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(msg);
	}
	
	/**
	 * 显示单条数据
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@PostMapping("/query")
	public void query(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String msg = null;
		try {
			Short fid = Short.parseShort(request.getParameter("fid"));
			List<FileUpload> fileUploadList = fileService.selectByPrimaryKey(fid);
			String detail = fileUploadList.get(0).getDetail();
			String title = fileUploadList.get(0).getTitle();
			//需将数据中的双引号添加转义符才能以JSON格式传递到前端显示
			detail = detail.replaceAll("\"", "\\\\\"");
			msg = "{\"msg\":\""+detail+"\",\"title\":\""+title+"\",\"fid\":\""+fid+"\",\"value\":1}";			
		} catch (Exception e) {
			// TODO: handle exception
			msg = "{\"msg\":\"数据显示失败\",\"value\":0}";
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(msg);
	}
	
	/**
	 * 更新数据
	 * 将数据返回富文本编辑器进行再编辑
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/replace")
	public String replace(HttpServletRequest request){
		Short fid = Short.parseShort(request.getParameter("fid"));
		List<FileUpload> fileUploadList = fileService.selectByPrimaryKey(fid);
		String detail = fileUploadList.get(0).getDetail();
		String title = fileUploadList.get(0).getTitle();
		request.setAttribute("detail", detail);
		request.setAttribute("title", title);
		request.setAttribute("fid", fid);
		return editor();
	}
	
	/**
	 * 删除数据
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Short fid = Short.parseShort(request.getParameter("fid"));
		int i = fileService.deleteByPrimaryKey(fid);
		String msg = "{\"msg\":"+i+"}";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(msg);
	}
	
	/**
	 * 图片上传方法
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@PostMapping("/uploadImg")
	public void uploadImg(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String msg;
		/**上传图片**/
		msg = imgUpload.imgUpload(request);
		/***********/
        //返回图片url地址
        response.getWriter().print(msg);
	}
	
	/**
	 * 主页分页显示数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@PostMapping("/pagedata")
	public void page(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		String strFid = request.getParameter("fid");
		PageInfo<FileUpload> bean = fileService.findItemByPage(currentPage,pageSize,strFid);
		String data = JSON.toJSONString(bean);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(data);
	}
	
	/**
	 * 获取总页码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@PostMapping("/weiye")
	public void weiye(HttpServletRequest request,HttpServletResponse response) throws IOException{
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		List<FileUpload> list = fileService.selectAll();
		int totalPage = (list.size()+pageSize-1)/pageSize;
		String pages = "{\"pages\":"+totalPage+"}";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(pages);
	}
}
