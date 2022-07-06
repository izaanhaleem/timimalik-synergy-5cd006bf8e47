package com.hisdu.SESCluster.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usman Kurd on 7/12/2018.
 */

public class SixToTenWeeksObject implements Parcelable {
    private String pentaOneVaccinated;
    private String pentaOneVaccinatedDate;
    private String pcvTenOneVaccinated;
    private String pcvTenOneVaccinatedDate;
    private String opVOneVaccinated;
    private String opVOneVaccinatedDate;
    private String rotaVaccineOneVaccinated;
    private String rotaVaccineOneVaccinatedDate;
    private String remark;

    public SixToTenWeeksObject() {
    }

    protected SixToTenWeeksObject(Parcel in) {
        pentaOneVaccinated = in.readString();
        pentaOneVaccinatedDate = in.readString();
        pcvTenOneVaccinated = in.readString();
        pcvTenOneVaccinatedDate = in.readString();
        opVOneVaccinated = in.readString();
        opVOneVaccinatedDate = in.readString();
        rotaVaccineOneVaccinated = in.readString();
        rotaVaccineOneVaccinatedDate = in.readString();
        remark = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pentaOneVaccinated);
        dest.writeString(pentaOneVaccinatedDate);
        dest.writeString(pcvTenOneVaccinated);
        dest.writeString(pcvTenOneVaccinatedDate);
        dest.writeString(opVOneVaccinated);
        dest.writeString(opVOneVaccinatedDate);
        dest.writeString(rotaVaccineOneVaccinated);
        dest.writeString(rotaVaccineOneVaccinatedDate);
        dest.writeString(remark);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SixToTenWeeksObject> CREATOR = new Creator<SixToTenWeeksObject>() {
        @Override
        public SixToTenWeeksObject createFromParcel(Parcel in) {
            return new SixToTenWeeksObject(in);
        }

        @Override
        public SixToTenWeeksObject[] newArray(int size) {
            return new SixToTenWeeksObject[size];
        }
    };

    public String getPentaOneVaccinated() {
        return pentaOneVaccinated;
    }

    public void setPentaOneVaccinated(String pentaOneVaccinated) {
        this.pentaOneVaccinated = pentaOneVaccinated;
    }

    public String getPentaOneVaccinatedDate() {
        return pentaOneVaccinatedDate;
    }

    public void setPentaOneVaccinatedDate(String pentaOneVaccinatedDate) {
        this.pentaOneVaccinatedDate = pentaOneVaccinatedDate;
    }

    public String getPcvTenOneVaccinated() {
        return pcvTenOneVaccinated;
    }

    public void setPcvTenOneVaccinated(String pcvTenOneVaccinated) {
        this.pcvTenOneVaccinated = pcvTenOneVaccinated;
    }

    public String getPcvTenOneVaccinatedDate() {
        return pcvTenOneVaccinatedDate;
    }

    public void setPcvTenOneVaccinatedDate(String pcvTenOneVaccinatedDate) {
        this.pcvTenOneVaccinatedDate = pcvTenOneVaccinatedDate;
    }

    public String getOpVOneVaccinated() {
        return opVOneVaccinated;
    }

    public void setOpVOneVaccinated(String opVOneVaccinated) {
        this.opVOneVaccinated = opVOneVaccinated;
    }

    public String getOpVOneVaccinatedDate() {
        return opVOneVaccinatedDate;
    }

    public void setOpVOneVaccinatedDate(String opVOneVaccinatedDate) {
        this.opVOneVaccinatedDate = opVOneVaccinatedDate;
    }

    public String getRotaVaccineOneVaccinated() {
        return rotaVaccineOneVaccinated;
    }

    public void setRotaVaccineOneVaccinated(String rotaVaccineOneVaccinated) {
        this.rotaVaccineOneVaccinated = rotaVaccineOneVaccinated;
    }

    public String getRotaVaccineOneVaccinatedDate() {
        return rotaVaccineOneVaccinatedDate;
    }

    public void setRotaVaccineOneVaccinatedDate(String rotaVaccineOneVaccinatedDate) {
        this.rotaVaccineOneVaccinatedDate = rotaVaccineOneVaccinatedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
