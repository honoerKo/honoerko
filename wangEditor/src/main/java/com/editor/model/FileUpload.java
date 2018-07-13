package com.editor.model;

public class FileUpload {
    private Short fid;

    private String title;

    private String detail;
    
    private Short code;

    public Short getFid() {
        return fid;
    }

    public void setFid(Short fid) {
        this.fid = fid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

	public Short getCode() {
		return code;
	}

	public void setCode(Short code) {
		this.code = code;
	}
}