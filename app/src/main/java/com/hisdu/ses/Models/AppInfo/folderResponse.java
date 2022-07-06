package com.hisdu.ses.Models.AppInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class folderResponse {

    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("List")
    @Expose
    private List<FolderModel> data = null;


    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<FolderModel> getData() {
        return data;
    }

    public void setData(List<FolderModel> data) {
        this.data = data;
    }

    public class FolderModel {

        @SerializedName("FolderId")
        @Expose
        private Integer FolderId;
        @SerializedName("FolderName")
        @Expose
        private String FolderName;

        public Integer getFolderId() {
            return FolderId;
        }

        public void setFolderId(Integer folderId) {
            FolderId = folderId;
        }

        public String getFolderName() {
            return FolderName;
        }

        public void setFolderName(String folderName) {
            FolderName = folderName;
        }
    }

}
