package com.hisdu.ses.Models.contactUs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class contactUsResponse {
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

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("PrimaryEmail")
        @Expose
        private String primaryEmail;
        @SerializedName("SecondaryEmail")
        @Expose
        private String secondaryEmail;
        @SerializedName("PrimaryContactNo")
        @Expose
        private String primaryContactNo;
        @SerializedName("SecondaryContactNo")
        @Expose
        private String secondaryContactNo;
        @SerializedName("CreatedBy")
        @Expose
        private Object createdBy;
        @SerializedName("CreatedOn")
        @Expose
        private Object createdOn;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getPrimaryEmail() {
            return primaryEmail;
        }

        public void setPrimaryEmail(String primaryEmail) {
            this.primaryEmail = primaryEmail;
        }

        public String getSecondaryEmail() {
            return secondaryEmail;
        }

        public void setSecondaryEmail(String secondaryEmail) {
            this.secondaryEmail = secondaryEmail;
        }

        public String getPrimaryContactNo() {
            return primaryContactNo;
        }

        public void setPrimaryContactNo(String primaryContactNo) {
            this.primaryContactNo = primaryContactNo;
        }

        public String getSecondaryContactNo() {
            return secondaryContactNo;
        }

        public void setSecondaryContactNo(String secondaryContactNo) {
            this.secondaryContactNo = secondaryContactNo;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

        public Object getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(Object createdOn) {
            this.createdOn = createdOn;
        }

    }


}
