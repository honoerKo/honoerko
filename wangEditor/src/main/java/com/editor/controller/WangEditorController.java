package com.editor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.editor.service.IFileUploadService;
import com.editor.utils.ImgUploadUtil;
import com.editor.model.FileUpload;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/we")
@Api(tags="编辑器控制类")
public class WangEditorController {

	@Resource
	private IFileUploadService fileService;
	@Autowired
	private ImgUploadUtil imgUpload;
	
	@RequestMapping(value="/editor",method=RequestMethod.GET)
	@ApiOperation(value="请求编辑器页面")
	public String editor(){
		return "page/editor";
	}
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	@ApiOperation(value="请求主页")
	public String detail(){
		return "page/detail";
	}
	
	/**
	 * 将编辑器内容存储进数据库
	 * @param fid 序号
	 * @param title 标题
	 * @param detail 内容
	 */
	@PostMapping("/saveOrReplace")
	@ApiOperation(value="将编辑器内容存储进数据库",notes="新增or重新编辑后的内容")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="fid",value="序号",required=true,dataType="String"),
		@ApiImplicitParam(paramType="query",name="title",value="标题",required=true,dataType="String"),
		@ApiImplicitParam(paramType="query",name="detail",value="内容",required=true,dataType="String")
	})
	public void saveOrReplace(@RequestParam String fid,@RequestParam String title,@RequestParam String detail,HttpServletResponse response){
		try {
			String msg = null;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询最后一次发布的一条数据
	 */
	@PostMapping("/queryByMaxId")
	@ApiOperation(value="查询最后一次发布的一条数据")
	public void queryByMaxId(HttpServletResponse response){
		try {
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据fid查询指定数据
	 * @param fid 序号
	 */
	@PostMapping("/query")
	@ApiOperation(value="根据fid查询指定数据")
	@ApiImplicitParam(paramType="query",value="fid",name="序号",required=true,dataType="String")
	public void query(@RequestParam String fid,HttpServletResponse response){
		try {
			String msg = null;
			try {
				Short id = Short.parseShort(fid);
				List<FileUpload> fileUploadList = fileService.selectByPrimaryKey(id);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新数据
	 * @param fid 序号
	 * @return 将数据返回富文本编辑器
	 */
	@GetMapping("/replace")
	@ApiOperation(value="更新数据",notes="将数据返回富文本编辑器")
	@ApiImplicitParam(paramType="query",value="fid",name="序号",required=true,dataType="String")
	public String replace(@RequestParam String fid,HttpServletRequest request){
		Short id = Short.parseShort(fid);
		List<FileUpload> fileUploadList = fileService.selectByPrimaryKey(id);
		String detail = fileUploadList.get(0).getDetail();
		String title = fileUploadList.get(0).getTitle();
		request.setAttribute("detail", detail);
		request.setAttribute("title", title);
		request.setAttribute("fid", fid);
		return editor();
	}
	
	/**
	 * 删除数据
	 * @param fid 序号
	 */
	@PostMapping("/delete")
	@ApiOperation(value="删除数据")
	@ApiImplicitParam(paramType="query",value="fid",name="序号",required=true,dataType="String")
	public void delete(@RequestParam String fid,HttpServletResponse response){
		try {
			Short id = Short.parseShort(fid);
			int i = fileService.deleteByPrimaryKey(id);
			String msg = "{\"msg\":"+i+"}";
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().print(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片上传
	 */
	@PostMapping("/uploadImg")
	@ApiOperation(value="图片上传方法",notes="编辑器上传本地图片接口")
	@ApiImplicitParam(paramType="query",value="username",name="用户名",required=true,dataType="String")
	public void uploadImg(@RequestParam String username,HttpServletResponse response,HttpServletRequest request){
        try {
        	/**上传图片**/
        	 String msg = imgUpload.imgUpload(username,request);
        	/***********/
        	//返回图片url地址
			response.getWriter().print(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 分页
	 * @param pageSize 每页条数
	 * @param currentPage 当前页码
	 * @param fid 序号
	 */
	@PostMapping("/pagedata")
	@ApiOperation(value="分页接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",value="pageSize",name="每页条数",required=true,dataType="Integer"),
		@ApiImplicitParam(paramType="query",value="currentPage",name="当前页码",required=true,dataType="Integer"),
		@ApiImplicitParam(paramType="query",value="fid",name="序号",required=true,dataType="String")
	})
	public void page(@RequestParam Integer pageSize,@RequestParam Integer currentPage,@RequestParam String fid,HttpServletResponse response){
		try {
			PageInfo<FileUpload> bean = fileService.findItemByPage(currentPage,pageSize,fid);
			String data = JSON.toJSONString(bean);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().print(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取总页码
	 * @param pageSize 每页条数
	 */
	@PostMapping("/weiye")
	@ApiOperation(value="分页数据获取总页码")
	@ApiImplicitParam(paramType="query",value="pageSize",name="每页条数",required=true,dataType="Integer")
	public void weiye(@RequestParam Integer pageSize,HttpServletResponse response){
		try {
			List<FileUpload> list = fileService.selectAll();
			int totalPage = (list.size()+pageSize-1)/pageSize;
			String pages = "{\"pages\":"+totalPage+"}";
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getWriter().print(pages);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
