package com.editor.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component
public class ImgUploadUtil {
	
	/**
	 * 图片上传方法
	 * @param request
	 * @param fileName
	 * @param msg
	 * @return 图片本地相对地址
	 * @throws IOException
	 */
	public String imgUpload(HttpServletRequest request) throws IOException{
		String username = request.getParameter("user");
		//建立文件存储地址
		//String faFileName = getDate();
		String filePath = ConstantUtil.PATH + username;
		//检查地址是否存在，若不存在则创建文件夹
		File file = new File(filePath);
        if (!file.exists()) file.mkdirs();
        //从request域中取出图片
		MultipartHttpServletRequest params = ((MultipartHttpServletRequest)request);
        List<MultipartFile> files = params.getFiles("myFileName");
        //执行文件上传操作
        for (MultipartFile multipartFile : files) {
        	if (!multipartFile.isEmpty()) {
                try {
                    byte[] bytes = multipartFile.getBytes();
                    String fileName = UUID.randomUUID().toString() + multipartFile.getOriginalFilename();         
        		    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath + "/" + fileName));      
        		    bos.write(bytes);
        		    bos.close();
        		    return filePath.substring(2) + "/" + fileName;
                } catch (Exception e) {      
                    return "error|上传失败";
                }      
            } else {      
                return "error|未获取到文件";
            }
		}
        return "error|NULL";
	}
}
