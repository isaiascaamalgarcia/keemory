package com.example.bitware.keemorycuidador;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Izzy-Izumi on 02/08/2015.
 */
public interface CarerService {
    @POST("/carer")
    public void createCarer(@Body Carer body, Callback<Carer> callBack);
}
