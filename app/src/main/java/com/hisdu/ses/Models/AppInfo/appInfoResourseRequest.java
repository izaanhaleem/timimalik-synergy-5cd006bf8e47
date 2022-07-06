package com.hisdu.ses.Models.AppInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class appInfoResourseRequest {

    @SerializedName("PageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("totalRecords")
    @Expose
    private Integer totalRecords;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("pageCount")
    @Expose
    private Integer pageCount;
    @SerializedName("queryString")
    @Expose
    private String queryString;
    @SerializedName("FolderId")
    @Expose
    private Integer FolderId;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Integer getFolderId() {
        return FolderId;
    }

    public void setFolderId(Integer folderId) {
        FolderId = folderId;
    }
}
