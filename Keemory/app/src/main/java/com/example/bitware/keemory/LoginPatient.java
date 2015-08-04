package com.example.bitware.keemory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginPatient extends ActionBarActivity {

    EditText et_ckp;
    String ckp, ckpPreference;
    private static final String TAG = "PatientLogin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_patient);
        checkPreferences();
        et_ckp = (EditText)findViewById(R.id.et_ckp);
        ckp = et_ckp.getText().toString();
    }

    public void LogOnserver(){
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint("http://192.168.0.7:9000");
        RestAdapter restAdapter = builder.build();

        PatientService service = restAdapter.create(PatientService.class);

        service.getPatientInfo(ckp, new Callback<List<Patient>>() {
            @Override
            public void success(List<Patient> patient, Response response) {
                try {
                    if(patient.get(0) != null){
                        //savePreferences();
                        finish();
                        startActivity(new Intent(getApplicationContext(), Keemory.class));
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error al capturar los datos: " + e, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
                Toast.makeText(getApplicationContext(), "Fatal error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void savePreferences(){

        SharedPreferences miPreferencia = getSharedPreferences("preferenceCKP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = miPreferencia.edit();
        editor.putString("ckp", ckp);
        editor.commit();

    }

    public void checkPreferences(){

        SharedPreferences miPreferencia = getSharedPreferences("preferenceCKP", Context.MODE_PRIVATE);
        ckpPreference = miPreferencia.getString("ckp", "");
        try {
            if (ckpPreference.isEmpty()) {
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterPatient.class));
            }else{
                finish();
                startActivity(new Intent(getApplicationContext(), Keemory.class));
            }
        }catch(Exception e){}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
