package com.example.bitware.keemorycuidador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Keemory extends ActionBarActivity {

    RecyclerView rv;
    private static final String TAG = "carer";
    private String getPreferencia;
    private int getCarerId;
    private int listSize = 0;
    private String name = "";
    private int ic_photo = 0;
    private int ic_call = 0;
    private int ic_gps = 0;
    private int patientId;
    public int[] idPatient;
    public int[] position;
    private PatientAdapter pa;
    private ArrayList<Patient> patientList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pa = new PatientAdapter(getApplication());
        setContentView(R.layout.activity_keemory);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        rv = (RecyclerView)findViewById(R.id.rv_patients);

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //cargarPreferencias();
        fillRecyclerView();
    }

    public void fillRecyclerView(){
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint("http://192.168.1.66:9000");
        RestAdapter restAdapter = builder.build();

        PatientService service = restAdapter.create(PatientService.class);

        service.getPatients(1, new Callback<List<Patient>>() {
            @Override
            public void success(List<Patient> patientData, Response response) {
                try {

                    listSize = patientData.size();
                    idPatient = new int[listSize];
                    position = new int[listSize];
                    for (int i = 0; i < listSize; i++) {

                        name = patientData.get(i).getName();
                        ic_photo = R.drawable.ic_launcher;
                        ic_call = R.drawable.lg_keemory;
                        ic_gps = R.drawable.ic_launcher;
                        patientId = patientData.get(i).getId();

                        Patient patientInfo = new Patient();
                        patientInfo.setName(name);
                        patientInfo.setId(ic_photo);
                        patientData.get(i).getLat();
                        patientData.get(i).getLon();

                        idPatient[i] = patientId;
                        position[i] = i;

                        patientList.add(patientInfo);

                    }

                    rv.setAdapter(pa);
                    pa.setPatientList(patientList);

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

    public void cargarPreferencias() {

        SharedPreferences miPreferencia = getSharedPreferences("preferenceToken", Context.MODE_PRIVATE);
        getPreferencia = miPreferencia.getString("token", "");

        SharedPreferences miPreferencia2 = getSharedPreferences("preferenceId", Context.MODE_PRIVATE);
        getCarerId = miPreferencia2.getInt("UserId", -1);

        try {
            if (getPreferencia.isEmpty())
                Toast.makeText(Keemory.this, "Unauthorized  ACCES_TOKEN required", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_keemory, menu);
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
