package com.example.bitware.keemorycuidador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Izzy-Izumi on 01/08/2015.
 */
public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientHolder> {

    private final LayoutInflater inflater;
    ArrayList<Patient> patients = new ArrayList<>();
    Bitmap bitmap;

    public PatientAdapter(Context contex) {
        inflater = LayoutInflater.from(contex);
    }

    @Override
    public PatientHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.patient_row, viewGroup,false);
        PatientHolder holder = new PatientHolder(view);
        return holder;
    }

    public void setPatientList(ArrayList<Patient> patientList) {
        this.patients = patientList;
        notifyItemRangeChanged(0, patientList.size());

    }

    @Override
    public void onBindViewHolder(PatientHolder viewHolder, int i) {
        Patient currentPatiente = patients.get(i);
        URL url = null;
        try {
            url = new URL("https://www.colourbox.com/preview/11111197-brain-icon-vector-on-black-background.jpg");
            URLConnection conn = url.openConnection();
            conn.connect();
            conn.setReadTimeout(1000);
            InputStream in = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(in,1024*8);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            int len=0;
            byte[] buffer = new byte[1024];
            while((len = bis.read(buffer)) != -1){
                out.write(buffer, 0, len);
            }
            out.close();
            bis.close();

            byte[] data = out.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.ic_photo.setImageBitmap(bitmap);
        viewHolder.tv_name.setText(currentPatiente.getName());
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    class PatientHolder extends RecyclerView.ViewHolder{

        ImageView ic_photo;
        TextView tv_name;

        public PatientHolder(View itemView) {
            super(itemView);
            ic_photo = (ImageView) itemView.findViewById(R.id.ic_photo);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
