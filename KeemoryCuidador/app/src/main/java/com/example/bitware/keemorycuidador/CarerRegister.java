package com.example.bitware.keemorycuidador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class CarerRegister extends ActionBarActivity {


    private String ckcPreference, name, address, phone, ckc;
    private EditText nameCarer;
    private EditText addressCarer;
    private EditText phoneCarer;
    private Button bt_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carer_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        checkPreferences();
        nameCarer = (EditText)findViewById(R.id.et_name);
        addressCarer = (EditText)findViewById(R.id.et_address);
        phoneCarer = (EditText) findViewById(R.id.et_phone);
        ckc = UUID.randomUUID().toString();
        bt_save = (Button)findViewById(R.id.bt_save);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameCarer.getText().toString();
                address = addressCarer.getText().toString();
                phone = phoneCarer.getText().toString();
                if(name.trim().equals(""))
                    nameCarer.setError("El nombre es requerido");
                else if (address.trim().equals(""))
                    addressCarer.setError("El domicilio es requerido");
                else if (phone.trim().equals(""))
                    phoneCarer.setError("El telefono es requerido");
                else
                    saveDataOnserver(name, address, phone, ckc);
            }
        });
    }

    public void saveDataOnserver(String name, String address, String phone, String ckc){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://192.168.1.66:9000")
                .build();
        CarerService post = restAdapter.create(CarerService.class);
        Carer carer = new Carer();
        carer.setName(name);
        carer.setAddress(address);
        carer.setCkc(ckc);
        carer.setPhone(phone);

        post.createCarer(carer, new Callback<Carer>() {
            @Override
            public void success(Carer carer, Response response) {
                System.out.println("carerCreated: " + response);
                savePreferences();

                startActivity(new Intent(getApplicationContext(),Keemory.class));
                finish();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                System.out.println(retrofitError.getMessage());
            }
        });
    }

    public void savePreferences(){

        SharedPreferences miPreferencia = getSharedPreferences("preferenceCKC", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = miPreferencia.edit();
        editor.putString("ckc", ckc);
        editor.commit();

    }

    public void checkPreferences(){

        SharedPreferences miPreferencia = getSharedPreferences("preferenceCKC", Context.MODE_PRIVATE);
        ckcPreference = miPreferencia.getString("ckc", "");
        try {
            if (ckcPreference.isEmpty()) {
                Toast.makeText(CarerRegister.this, "Es necesario registrarse", Toast.LENGTH_LONG).show();
            }else{
                finish();
                startActivity(new Intent(getApplicationContext(), Keemory.class));
            }
        }catch(Exception e){}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_carer_register, menu);
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
