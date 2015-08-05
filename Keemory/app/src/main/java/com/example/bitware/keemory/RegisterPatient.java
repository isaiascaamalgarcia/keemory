package com.example.bitware.keemory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RegisterPatient extends ActionBarActivity {

    Button bt_save;
    ImageView foto, folder;
    Bitmap resizeImage;
    TextView add;
    EditText et_name, et_addres, et_phone;
    private String ckpPreference, name, address, phone, ckp, ruta_foto, photoB64 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        checkPreferences();
        folder = (ImageView)findViewById(R.id.ic_folder);
        add = (TextView)findViewById(R.id.tv_carpeta);
        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                establece_photo();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                establece_photo();
            }
        });
        foto = (ImageView)findViewById(R.id.ic_camera);
        ckp = UUID.randomUUID().toString();
        ckp = ckp.substring(0,8);
        bt_save = (Button)findViewById(R.id.bt_save);
        et_name = (EditText)findViewById(R.id.et_name);
        et_addres = (EditText)findViewById(R.id.et_address);
        et_phone = (EditText)findViewById(R.id.et_phone);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = et_name.getText().toString();
                address = et_addres.getText().toString();
                phone = et_phone.getText().toString();
                if(name.trim().equals(""))
                    et_name.setError("El nombre es requerido");
                else if (address.trim().equals(""))
                    et_addres.setError("El domicilio es requerido");
                else if (phone.trim().equals(""))
                    et_phone.setError("El teléfono es requerido");
                else
                    saveDataOnserver(name, address, phone, ckp);
            }
        });
        /*FacebookSdk.sdkInitialize(getApplicationContext());
        fb newFragment = new fb();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.newFrag, newFragment);
        transaction.commit();
        Bitmap image = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.lg_keemory);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        ShareDialog.show(this, content);*/
    }

    public void establece_photo(){
        final CharSequence[] foto_options ={"Toma una foto","Elige una de galeria","Cerrar"};
        AlertDialog.Builder alert_b = new AlertDialog.Builder(RegisterPatient.this);
        alert_b.setTitle("Añadir fotografía");
        alert_b.setItems(foto_options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (foto_options[item].equals("Toma una foto")) {
                    Intent ii = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(android.os.Environment.getExternalStorageDirectory(),
                            "foto_temp.jpg");
                    ii.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(ii, 1);
                } else if (foto_options[item].equals("Elige una de galeria")) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (foto_options[item].equals("Cerrar")) {
                    dialog.dismiss();
                }
            }
        });
        alert_b.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode ==1) {
                File file = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : file.listFiles()) {
                    if (temp.getName().equals("foto_temp.jpg")) {
                        file = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);
                    resizeImage = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
                    foto.setImageBitmap(resizeImage);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    resizeImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();
                    String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
                    photoB64 = imageEncoded;

                    File ruta = new File(android.os.Environment.getExternalStorageDirectory()
                            + File.separator + "Keemory");
                    if (!ruta.exists()) {
                        ruta.mkdirs();
                    }
                    file.delete();
                    OutputStream outfile = null;
                    File fi = new File(ruta, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    ruta_foto = fi.toString();
                    try {
                        outfile = new FileOutputStream(fi, true);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outfile);
                        outfile.flush();
                        outfile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(requestCode == 2){
                Uri ImgSeleccion = data.getData();
                String[] filepath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(ImgSeleccion,filepath,null,null,null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filepath[0]);
                String picturepath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturepath));
                //Log.w("path of image from gallery......******************.........",
                // picturepath+ "");
                resizeImage = Bitmap.createScaledBitmap(thumbnail, 200, 200, false);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resizeImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
                photoB64 = imageEncoded;
                Log.w("b64: .",photoB64+ "");
                foto.setImageBitmap(resizeImage);
                try{
                    File sd = Environment.getExternalStorageDirectory();
                    if(sd.canWrite()){
                        String destinoImagen = "/Keemory/"
                                +String.valueOf(System.currentTimeMillis() + ".jpg");
                        File ruta = new File(android.os.Environment.getExternalStorageDirectory()
                                + File.separator+"Keemory");
                        if(!ruta.exists()){
                            ruta.mkdirs();
                        }
                        File source = new File(picturepath);
                        File destino = new File(sd,destinoImagen);
                        ruta_foto = destino.toString();
                        if(source.exists()){
                            FileChannel src = new FileInputStream(source).getChannel();
                            FileChannel dst = new FileOutputStream(destino).getChannel();
                            dst.transferFrom(src,0,src.size());
                            src.close();
                            dst.close();
                        }
                    }
                }catch (Exception e){
                }
            }
        }

    }

    public void saveDataOnserver(String name, String address, String phone, String ckc){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://192.168.104.81:9000")
                .build();
        PatientService post = restAdapter.create(PatientService.class);
        Patient patient = new Patient();
        patient.setName(name);
        patient.setAddress(address);
        patient.setCkp(ckc);
        patient.setBase64(photoB64+"=");
        patient.setPhone(phone);
        patient.setType("jpg");

        post.createPatient(patient, new Callback<Patient>() {
            @Override
            public void success(Patient patient, Response response) {
                System.out.println("Patient Created: " + response);
                //savePreferences();
                Toast.makeText(RegisterPatient.this, "Tu ckp es: " + ckp, Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Keemory.class));
                finish();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                System.out.println(retrofitError.getMessage());
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
                Toast.makeText(RegisterPatient.this, "Es necesario registrarse", Toast.LENGTH_LONG).show();
            }else{
                finish();
                startActivity(new Intent(getApplicationContext(), Keemory.class));
            }
        }catch(Exception e){}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_patient, menu);
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
