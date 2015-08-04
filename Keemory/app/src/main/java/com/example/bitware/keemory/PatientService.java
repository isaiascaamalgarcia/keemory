package com.example.bitware.keemory;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Izzy-Izumi on 03/08/2015.
 */
public interface PatientService {
    @POST("/patient")
    public void createPatient(@Body Patient body, Callback<Patient> callBack);

    @GET("/patients/{ckp}")
    public void getPatientInfo(@Path("ckp")String ckp, Callback<List<Patient>> callBack);
}
