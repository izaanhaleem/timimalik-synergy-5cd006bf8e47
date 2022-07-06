package com.hisdu.SESCluster.utils;


import com.hisdu.SESCluster.objects.support.SpinnerObject;

import java.util.Comparator;

public class SortBy implements Comparator<SpinnerObject> {

    @Override
    public int compare(SpinnerObject spinnerObject1, SpinnerObject spinnerObject2) {
        return spinnerObject1.getTitle().compareToIgnoreCase(spinnerObject2.getTitle());
    }


}