package com.hisdu.SESCluster.communication;

/**
 * Created by Usman Kurd on 6/12/2016.
 */
public class RequestObject {
    private String paramName;
    private String paramValue;

    public RequestObject(){}

    public RequestObject(String paramName, String paramValue){
        this.setParamName(paramName);
        this.setParamValue(paramValue);
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "RequestObject{" +
                "paramName='" + paramName + '\'' +
                ", paramValue='" + paramValue + '\'' +
                '}';
    }
}
