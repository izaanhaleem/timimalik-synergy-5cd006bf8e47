package com.hisdu.SESCluster.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usman Kurd on 7/12/2018.
 */

public class ZeroToFortyDaysObject implements Parcelable {
    private String opvVaccinated;
    private String bcgVaccinated;
    private String hepbVaccinated;
    private String opvVaccinationDate;
    private String bcgVaccinationDate;
    private String hepVaccinationDate;
    private String remarks;

    public ZeroToFortyDaysObject() {
    }

    protected ZeroToFortyDaysObject(Parcel in) {
        opvVaccinated = in.readString();
        bcgVaccinated = in.readString();
        hepbVaccinated = in.readString();
        opvVaccinationDate = in.readString();
        bcgVaccinationDate = in.readString();
        hepVaccinationDate = in.readString();
        remarks = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(opvVaccinated);
        dest.writeString(bcgVaccinated);
        dest.writeString(hepbVaccinated);
        dest.writeString(opvVaccinationDate);
        dest.writeString(bcgVaccinationDate);
        dest.writeString(hepVaccinationDate);
        dest.writeString(remarks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ZeroToFortyDaysObject> CREATOR = new Creator<ZeroToFortyDaysObject>() {
        @Override
        public ZeroToFortyDaysObject createFromParcel(Parcel in) {
            return new ZeroToFortyDaysObject(in);
        }

        @Override
        public ZeroToFortyDaysObject[] newArray(int size) {
            return new ZeroToFortyDaysObject[size];
        }
    };

    public String getHepbVaccinated() {
        return hepbVaccinated;
    }

    public void setHepbVaccinated(String hepbVaccinated) {
        this.hepbVaccinated = hepbVaccinated;
    }

    public String getOpvVaccinated() {
        return opvVaccinated;
    }

    public void setOpvVaccinated(String opvVaccinated) {
        this.opvVaccinated = opvVaccinated;
    }

    public String getBcgVaccinated() {
        return bcgVaccinated;
    }

    public void setBcgVaccinated(String bcgVaccinated) {
        this.bcgVaccinated = bcgVaccinated;
    }

    public String getOpvVaccinationDate() {
        return opvVaccinationDate;
    }

    public void setOpvVaccinationDate(String opvVaccinationDate) {
        this.opvVaccinationDate = opvVaccinationDate;
    }

    public String getBcgVaccinationDate() {
        return bcgVaccinationDate;
    }

    public void setBcgVaccinationDate(String bcgVaccinationDate) {
        this.bcgVaccinationDate = bcgVaccinationDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getHepVaccinationDate() {
        return hepVaccinationDate;
    }

    public void setHepVaccinationDate(String hepVaccinationDate) {
        this.hepVaccinationDate = hepVaccinationDate;
    }
}
