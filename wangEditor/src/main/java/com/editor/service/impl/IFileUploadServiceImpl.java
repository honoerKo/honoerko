package com.editor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.editor.dao.FileUploadMapper;
import com.editor.model.FileUpload;
import com.editor.service.IFileUploadService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class IFileUploadServiceImpl implements IFileUploadService {

	@Resource
	private FileUploadMapper fileMapper;
	
	public int deleteByPrimaryKey(Short fid) {
		int i;
		// TODO Auto-generated method stub
		try {
			i = fileMapper.deleteByPrimaryKey(fid);
		} catch (Exception e) {
			// TODO: handle exception
			i = 0;
		}
		return i;
	}

	public int insert(FileUpload record) {
		// TODO Auto-generated method stub
		int i;
		try {
			i = fileMapper.insert(record);
		} catch (Exception e) {
			// TODO: handle exception
			i = 0;
		}
		return i;
	}

	public int insertSelective(FileUpload record) {
		// TODO Auto-generated method stub
		int i;
		try {
			i = fileMapper.insertSelective(record);
		} catch (Exception e) {
			// TODO: handle exception
			i = 0;
		}
		return i;
	}

	public List<FileUpload> selectByPrimaryKey(Short fid) {
		// TODO Auto-generated method stub
		return fileMapper.selectByPrimaryKey(fid);
	}

	public List<FileUpload> selectAll() {
		// TODO Auto-generated method stub
		return fileMapper.selectAll();
	}

	public FileUpload selectByMaxId() {
		// TODO Auto-generated method stub
		return fileMapper.selectByMaxId();
	}

	public int updateByPrimaryKeySelective(FileUpload record) {
		// TODO Auto-generated method stub
		int i;
		try {
			i = fileMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			// TODO: handle exception
			i = 0;
		}
		return i;
	}

	public int updateByPrimaryKeyWithBLOBs(FileUpload record) {
		// TODO Auto-generated method stub
		int i;
		try {
			i = fileMapper.updateByPrimaryKeyWithBLOBs(record);
		} catch (Exception e) {
			// TODO: handle exception
			i = 0;
		}
		return i;
	}

	public int updateByPrimaryKey(FileUpload record) {
		// TODO Auto-generated method stub
		int i;
		try {
			i = fileMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			// TODO: handle exception
			i = 0;
		}
		return i;
	}

	/**
	 * 分页查询+查询框
	 * str（序号）
	 */
	public PageInfo<FileUpload> findItemByPage(int currentPage, int pageSize, String str) {
		// TODO Auto-generated method stub
		List<FileUpload> all = null;
		PageHelper.startPage(currentPage, pageSize);
		if("".equals(str) || str == null){
			all = fileMapper.selectAll();	
		}else{
			Short fid = Short.parseShort(str);
			all = fileMapper.selectByPrimaryKey(fid);
		}
		PageInfo<FileUpload> pageData = new PageInfo<FileUpload>(all);
		return pageData;
	}

}
