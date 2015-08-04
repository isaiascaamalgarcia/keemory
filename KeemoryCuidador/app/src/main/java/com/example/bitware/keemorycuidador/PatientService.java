package com.example.bitware.keemorycuidador;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Created by Izzy-Izumi on 01/08/2015.
 */
public interface PatientService {

    @GET("/carer/{id}/patients")
    public void getPatients(@Path("id")int id, Callback<List<Patient>> cb);
}
