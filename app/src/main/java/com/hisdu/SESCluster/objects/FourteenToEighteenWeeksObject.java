package com.hisdu.SESCluster.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usman Kurd on 7/17/2018.
 */

public class FourteenToEighteenWeeksObject implements Parcelable {
    private String pentaVaccinated;
    private String pentaVaccinationDate;
    private String pcv10Vaccinated;
    private String pcv10VaccinationDate;
    private String opvVaccinated;
    private String opvVaccinationDate;
    private String ipvVaccinated;
    private String ipvVaccinationDate;
    private String remarks;

    public FourteenToEighteenWeeksObject() {
    }

    protected FourteenToEighteenWeeksObject(Parcel in) {
        pentaVaccinated = in.readString();
        pentaVaccinationDate = in.readString();
        pcv10Vaccinated = in.readString();
        pcv10VaccinationDate = in.readString();
        opvVaccinated = in.readString();
        opvVaccinationDate = in.readString();
        ipvVaccinated = in.readString();
        ipvVaccinationDate = in.readString();
        remarks = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pentaVaccinated);
        dest.writeString(pentaVaccinationDate);
        dest.writeString(pcv10Vaccinated);
        dest.writeString(pcv10VaccinationDate);
        dest.writeString(opvVaccinated);
        dest.writeString(opvVaccinationDate);
        dest.writeString(ipvVaccinated);
        dest.writeString(ipvVaccinationDate);
        dest.writeString(remarks);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FourteenToEighteenWeeksObject> CREATOR = new Creator<FourteenToEighteenWeeksObject>() {
        @Override
        public FourteenToEighteenWeeksObject createFromParcel(Parcel in) {
            return new FourteenToEighteenWeeksObject(in);
        }

        @Override
        public FourteenToEighteenWeeksObject[] newArray(int size) {
            return new FourteenToEighteenWeeksObject[size];
        }
    };

    public String getPentaVaccinated() {
        return pentaVaccinated;
    }

    public void setPentaVaccinated(String pentaVaccinated) {
        this.pentaVaccinated = pentaVaccinated;
    }

    public String getPentaVaccinationDate() {
        return pentaVaccinationDate;
    }

    public void setPentaVaccinationDate(String pentaVaccinationDate) {
        this.pentaVaccinationDate = pentaVaccinationDate;
    }

    public String getPcv10Vaccinated() {
        return pcv10Vaccinated;
    }

    public void setPcv10Vaccinated(String pcv10Vaccinated) {
        this.pcv10Vaccinated = pcv10Vaccinated;
    }

    public String getPcv10VaccinationDate() {
        return pcv10VaccinationDate;
    }

    public void setPcv10VaccinationDate(String pcv10VaccinationDate) {
        this.pcv10VaccinationDate = pcv10VaccinationDate;
    }

    public String getOpvVaccinated() {
        return opvVaccinated;
    }

    public void setOpvVaccinated(String opvVaccinated) {
        this.opvVaccinated = opvVaccinated;
    }

    public String getOpvVaccinationDate() {
        return opvVaccinationDate;
    }

    public void setOpvVaccinationDate(String opvVaccinationDate) {
        this.opvVaccinationDate = opvVaccinationDate;
    }

    public String getIpvVaccinated() {
        return ipvVaccinated;
    }

    public void setIpvVaccinated(String ipvVaccinated) {
        this.ipvVaccinated = ipvVaccinated;
    }

    public String getIpvVaccinationDate() {
        return ipvVaccinationDate;
    }

    public void setIpvVaccinationDate(String ipvVaccinationDate) {
        this.ipvVaccinationDate = ipvVaccinationDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
