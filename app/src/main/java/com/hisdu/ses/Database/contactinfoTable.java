package com.hisdu.ses.Database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name = "contactinfoTable")
public class contactinfoTable extends Model {


    @SerializedName("PrimaryEmail")
    @Column(name = "PrimaryEmail")
    @Expose
    public String primaryEmail;
    @SerializedName("SecondaryEmail")
    @Column(name = "SecondaryEmail")
    @Expose
    public String secondaryEmail;
    @SerializedName("PrimaryContactNo")
    @Column(name = "PrimaryContactNo")
    @Expose
    public String primaryContactNo;
    @SerializedName("SecondaryContactNo")
    @Column(name = "SecondaryContactNo")
    @Expose
    public String secondaryContactNo;

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


//    public static contactinfoTable getAllcontactInfo(){
//        return (contactinfoTable) new Select()
//                .from(contactinfoTable.class)
//                .execute();
//    }




    public static List<contactinfoTable> getAllcontactInfo() {
        return new Select()
                .from(contactinfoTable.class)
                .execute();
    }
}
