package com.example.myapplication;

import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private String[] dataArray;

    // Default constructor with no parameters
    public SharedViewModel() {

    }

    public String[] getDataArray() {
        return dataArray;
    }

    public void setDataArray(String[] dataArray) {

        this.dataArray = dataArray;
    }
}


//   !!!   This class has been used to passing data between FRAGMENTS     !!!