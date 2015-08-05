package controllers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

import models.Patient;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by Kody on 02/08/2015.
 */
public class PatientController extends Controller {

    public Result createPatient() {
        Patient patient = Json.fromJson(request().body().asJson(), Patient.class);
        String base64 = patient.getBase64();
        base64 = base64.replace("=","");
        base64 = base64.replace(" ","");
        base64 = base64.replace("\n","");
        System.out.println(base64);
        
        byte[] bytes = Base64.getDecoder().decode(base64);
        String randomName = UUID.randomUUID().toString();
        String extension = patient.getType();
        String filename = randomName+"."+extension;

        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage bImageFromConvert = null;
        try {
            bImageFromConvert = ImageIO.read(in);
        } catch (IOException e){
            e.printStackTrace();
        }
        File outputFile = new File("public/photos/"+filename);
        try {
            
            ImageIO.write(bImageFromConvert,extension,outputFile);
        } catch (IOException e){
            e.printStackTrace();
        }
        String url = "/assets/photos/"+filename;
        patient.setUrl(url);
        patient.save();
        return ok(Json.toJson(patient));
    }

    public Result getAllPatients() {
        List<Patient> patients = Patient.find.all();
        return ok(Json.toJson(patients));
    }

    public Result getPatient(int id) {
        Patient patient = Patient.find.byId(id);
        if(patient==null){
            return notFound();
        }
        else {
            return ok(Json.toJson(patient));
        }
    }
}
