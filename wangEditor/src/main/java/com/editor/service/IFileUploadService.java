package com.editor.service;

import java.util.List;

import com.editor.model.FileUpload;
import com.github.pagehelper.PageInfo;


public interface IFileUploadService {

	int deleteByPrimaryKey(Short fid);

    int insert(FileUpload record);

    int insertSelective(FileUpload record);

    List<FileUpload> selectByPrimaryKey(Short fid);
    
    List<FileUpload> selectAll();
    
    FileUpload selectByMaxId();

    int updateByPrimaryKeySelective(FileUpload record);

    int updateByPrimaryKeyWithBLOBs(FileUpload record);

    int updateByPrimaryKey(FileUpload record);
    
    PageInfo<FileUpload> findItemByPage(int currentPage,int pageSize,String str);
}
