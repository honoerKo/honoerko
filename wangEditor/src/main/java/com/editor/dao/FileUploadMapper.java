package com.editor.dao;

import java.util.List;

import com.editor.model.FileUpload;

public interface FileUploadMapper {
    int deleteByPrimaryKey(Short fid);

    int insert(FileUpload record);

    int insertSelective(FileUpload record);

    List<FileUpload> selectByPrimaryKey(Short fid);

    List<FileUpload> selectAll();
    
    FileUpload selectByMaxId();
    
    int updateByPrimaryKeySelective(FileUpload record);

    int updateByPrimaryKeyWithBLOBs(FileUpload record);

    int updateByPrimaryKey(FileUpload record);
}