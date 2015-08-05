package controllers;

import models.Carer;
import models.Patient;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.*;

/**
 * Created by Kody on 02/08/2015.
 */
public class CarerController extends Controller {
	public Result getPatients(int carerId) {
		Carer carer = Carer.find.byId(carerId);
		if(carer ==null){
			return notFound();
		}
		List<Patient> listaPaciente;
		if(carer.getPatients() != null){
			listaPaciente = new ArrayList(carer.getPatients());
		}else {
			listaPaciente = new ArrayList();
		}
		
			return ok(Json.toJson(listaPaciente));
		
		
		
	}
    public Result createCarer() {
        Carer carer = Json.fromJson(request().body().asJson(), Carer.class);
        carer.save();
        return ok(Json.toJson(carer));
    }
    public Result getAllCarer() {
        List<Carer> carers = Carer.find.all();
        return ok(Json.toJson(carers));
    }
    public Result getCarer(int id){
        Carer carer = Carer.find.byId(id);
        if(carer ==null){
            return notFound();
        }else {
            return ok(Json.toJson(carer));
        }
    }
    public Result addPatient(String ckp, int idCarer) {
    	Patient patient = Patient.find.where().eq("ckp", ckp).findUnique();
    	if(patient == null){
    		return notFound("La clave no existe.");
    	}else {
    	Carer oldCarer = Carer.find.byId(idCarer);
    	ArrayList<Patient> pc;
    	if(oldCarer.getPatients() != null){
    		pc  = new ArrayList(oldCarer.getPatients());
    		if(pc.size() > 0){
    		for (Patient u : pc) {
                if (u.getCKP().equals(ckp)) {
                    return badRequest("Este usuario ya ha sido agregado previamente.");
                }
            }
    	}
    	}
    	else
    		pc= new ArrayList();

		

    	pc.add(patient);
    	List<Patient> plist = pc;
    	oldCarer.patients = plist;
    	oldCarer.update();
    	List<Patient> paciente = oldCarer.getPatients();
    	return ok(Json.toJson(paciente));
    }
    	
    }
}
