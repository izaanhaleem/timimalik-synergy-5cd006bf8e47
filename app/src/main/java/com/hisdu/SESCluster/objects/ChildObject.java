package com.hisdu.SESCluster.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usman Kurd on 7/12/2018.
 */

public class ChildObject implements Parcelable {
    private String ChildName;
    private String FatherName;
    private String age;
    private String remarks;
    private String cardAvailable;
    private String cardNo;
    private String vaccinated;
    private String mobileNo;
    private String CreatedBy;
    private String CreatedOn;
    private String HosueNo;
    private String front;
    private String back;
    private String uc;
    private Double Latitude;
    private Double Longitude;
    private ZeroToFortyDaysObject zeroToFortyDaysObject = new ZeroToFortyDaysObject();
    private SixToTenWeeksObject sixToTenWeeksObject = new SixToTenWeeksObject();
    private TenToFourteenWeeksObject tenToFourteenWeeksObject = new TenToFourteenWeeksObject();
    private FifteenToSixteenMonthsObject fifteenToSixteenMonthsObject = new FifteenToSixteenMonthsObject();
    private NineToTenMonthsObjects nineToTenMonthsObjects = new NineToTenMonthsObjects();
    private FourteenToEighteenWeeksObject fourteenToEighteenWeeksObject = new FourteenToEighteenWeeksObject();
    private BoosterDoseObject boosterDoseObject = new BoosterDoseObject();

    public ChildObject() {
    }

    protected ChildObject(Parcel in) {
        ChildName = in.readString();
        FatherName = in.readString();
        age = in.readString();
        remarks = in.readString();
        cardAvailable = in.readString();
        cardNo = in.readString();
        vaccinated = in.readString();
        mobileNo = in.readString();
        HosueNo = in.readString();
        uc = in.readString();
        front = in.readString();
        back = in.readString();
        Latitude= in.readDouble();
        Longitude= in.readDouble();

        zeroToFortyDaysObject = in.readParcelable(ZeroToFortyDaysObject.class.getClassLoader());
        sixToTenWeeksObject = in.readParcelable(SixToTenWeeksObject.class.getClassLoader());
        tenToFourteenWeeksObject = in.readParcelable(TenToFourteenWeeksObject.class.getClassLoader());
        fifteenToSixteenMonthsObject = in.readParcelable(FifteenToSixteenMonthsObject.class.getClassLoader());
        nineToTenMonthsObjects = in.readParcelable(NineToTenMonthsObjects.class.getClassLoader());
        fourteenToEighteenWeeksObject = in.readParcelable(FourteenToEighteenWeeksObject.class.getClassLoader());
        boosterDoseObject = in.readParcelable(BoosterDoseObject.class.getClassLoader());

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ChildName);
        dest.writeString(FatherName);
        dest.writeString(age);
        dest.writeString(remarks);
        dest.writeString(cardAvailable);
        dest.writeString(cardNo);
        dest.writeString(vaccinated);
        dest.writeString(mobileNo);
        dest.writeString(CreatedBy);
        dest.writeString(CreatedOn);
        dest.writeString(HosueNo);
        dest.writeString(uc);
        dest.writeString(front);
        dest.writeString(back);
        dest.writeDouble(Latitude);
        dest.writeDouble(Longitude);

        dest.writeParcelable(zeroToFortyDaysObject, flags);
        dest.writeParcelable(sixToTenWeeksObject, flags);
        dest.writeParcelable(tenToFourteenWeeksObject, flags);
        dest.writeParcelable(fifteenToSixteenMonthsObject, flags);
        dest.writeParcelable(nineToTenMonthsObjects, flags);
        dest.writeParcelable(fourteenToEighteenWeeksObject, flags);
        dest.writeParcelable(boosterDoseObject, flags);

    }

    public String getHouse_no() {
        return HosueNo;
    }

    public void setHouse_no(String house_no) {
        this.HosueNo = house_no;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChildObject> CREATOR = new Creator<ChildObject>() {
        @Override
        public ChildObject createFromParcel(Parcel in) {
            return new ChildObject(in);
        }

        @Override
        public ChildObject[] newArray(int size) {
            return new ChildObject[size];
        }
    };

    public String getUc() {
        return uc;
    }

    public void setUc(String uc) {
        this.uc = uc;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getChildName() {
        return ChildName;
    }

    public void setChildName(String childName) {
        ChildName = childName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCardAvailable() {
        return cardAvailable;
    }

    public void setCardAvailable(String cardAvailable) {
        this.cardAvailable = cardAvailable;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(String vaccinated) {
        this.vaccinated = vaccinated;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public ZeroToFortyDaysObject getZeroToFortyDaysObject() {
        return zeroToFortyDaysObject;
    }

    public void setZeroToFortyDaysObject(ZeroToFortyDaysObject zeroToFortyDaysObject) {
        this.zeroToFortyDaysObject = zeroToFortyDaysObject;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public SixToTenWeeksObject getSixToTenWeeksObject() {
        return sixToTenWeeksObject;
    }

    public void setSixToTenWeeksObject(SixToTenWeeksObject sixToTenWeeksObject) {
        this.sixToTenWeeksObject = sixToTenWeeksObject;
    }

    public TenToFourteenWeeksObject getTenToFourteenWeeksObject() {
        return tenToFourteenWeeksObject;
    }

    public void setTenToFourteenWeeksObject(TenToFourteenWeeksObject tenToFourteenWeeksObject) {
        this.tenToFourteenWeeksObject = tenToFourteenWeeksObject;
    }

    public FifteenToSixteenMonthsObject getFifteenToSixteenMonthsObject() {
        return fifteenToSixteenMonthsObject;
    }

    public void setFifteenToSixteenMonthsObject(FifteenToSixteenMonthsObject fifteenToSixteenMonthsObject) {
        this.fifteenToSixteenMonthsObject = fifteenToSixteenMonthsObject;
    }

    public NineToTenMonthsObjects getNineToTenMonthsObjects() {
        return nineToTenMonthsObjects;
    }

    public void setNineToTenMonthsObjects(NineToTenMonthsObjects nineToTenMonthsObjects) {
        this.nineToTenMonthsObjects = nineToTenMonthsObjects;
    }

    public FourteenToEighteenWeeksObject getFourteenToEighteenWeeksObject() {
        return fourteenToEighteenWeeksObject;
    }

    public void setFourteenToEighteenWeeksObject(FourteenToEighteenWeeksObject fourteenToEighteenWeeksObject) {
        this.fourteenToEighteenWeeksObject = fourteenToEighteenWeeksObject;
    }

    public BoosterDoseObject getBoosterDoseObject() {
        return boosterDoseObject;
    }

    public void setBoosterDoseObject(BoosterDoseObject boosterDoseObject) {
        this.boosterDoseObject = boosterDoseObject;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }
}
