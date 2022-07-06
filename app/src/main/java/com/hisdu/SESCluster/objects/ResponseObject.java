package com.hisdu.SESCluster.objects;

/**
 * Created by Usman Kurd on 7/12/2018.
 */

public class ResponseObject {
    private int Status;
    private String Message;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
