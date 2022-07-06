package com.hisdu.SESCluster.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usman Kurd on 7/12/2018.
 */

public class imagesObject implements Parcelable {
    private String image;

    public imagesObject() {
    }

    protected imagesObject(Parcel in) {
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<imagesObject> CREATOR = new Creator<imagesObject>() {
        @Override
        public imagesObject createFromParcel(Parcel in) {
            return new imagesObject(in);
        }

        @Override
        public imagesObject[] newArray(int size) {
            return new imagesObject[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Creator<imagesObject> getCREATOR() {
        return CREATOR;
    }
}
