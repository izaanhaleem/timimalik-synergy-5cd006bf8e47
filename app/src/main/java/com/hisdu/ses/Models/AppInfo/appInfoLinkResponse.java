package com.hisdu.ses.Models.AppInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class appInfoLinkResponse {

    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public class Data {
        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("fileCount")
        @Expose
        private Integer fileCount;
        @SerializedName("resourceMaterialDeail")
        @Expose
        private List<ResourceMaterialDeail> resourceMaterialDeail = null;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getFileCount() {
            return fileCount;
        }

        public void setFileCount(Integer fileCount) {
            this.fileCount = fileCount;
        }

        public List<ResourceMaterialDeail> getResourceMaterialDeail() {
            return resourceMaterialDeail;
        }

        public void setResourceMaterialDeail(List<ResourceMaterialDeail> resourceMaterialDeail) {
            this.resourceMaterialDeail = resourceMaterialDeail;
        }

    }



        public class ResourceMaterialDeail {

            @SerializedName("DetailId")
            @Expose
            private Integer detailId;
            @SerializedName("resourceMaterialId")
            @Expose
            private Integer resourceMaterialId;
            @SerializedName("filePath")
            @Expose
            private String filePath;
            @SerializedName("FileName")
            @Expose
            private String fileName;

            public Integer getDetailId() {
                return detailId;
            }

            public void setDetailId(Integer detailId) {
                this.detailId = detailId;
            }

            public Integer getResourceMaterialId() {
                return resourceMaterialId;
            }

            public void setResourceMaterialId(Integer resourceMaterialId) {
                this.resourceMaterialId = resourceMaterialId;
            }

            public String getFilePath() {
                return filePath;
            }

            public void setFilePath(String filePath) {
                this.filePath = filePath;
            }

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

        }



}
