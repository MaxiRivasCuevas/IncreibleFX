package org.example.gestionclinica.clientes;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import org.example.gestionclinica.RRHH.PersonalMedico;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.example.gestionclinica.Clinica.inicializarFirebase;

public class Paciente {
	private String RUT;
	private String nombre;
	private String contrasena;
	private String historial;
	private int edad;
	private String enfermedadCronica;
	private PersonalMedico medicoTratante;
	private ArrayList<Cita> citas;

	public Paciente(String RUT, String nombre, String contrasena, String historial, int edad, String enfermedadCronica) {
		this.RUT = RUT;
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.historial = historial;
		this.edad = edad;
		this.enfermedadCronica = enfermedadCronica;
		this.citas=new ArrayList<>();
	}

	public void setMedicoTratante(PersonalMedico medicoTratante) {
		this.medicoTratante = medicoTratante;
	}

	public void agregarCita(Cita cita) {
		this.citas.add(cita);
	}

	public void quitarCita(int nivelAcceso,int numCita) {
		if(nivelAcceso<=2){
			citas.remove((numCita-1));
			System.out.println("Se removio la cita");
		}else {
			System.out.println("El nivel de acceso no es el requerido");
		}
	}

	public void cambarMedicoTratante(PersonalMedico medico) {
		this.medicoTratante=medico;
		System.out.println("Se ha tranferido de medico");
		System.out.println("Nuevo medico: "+medico.getNombre());
	}

	public void agregarInfo(String info){
		this.historial += info + "\n";
	}

	public void agregarEnfermedadCronica(String info){
		this.enfermedadCronica += info + "\n";
	}

	public String citasToString(){
		String resultado = "";
		for(Cita cita:citas) {
			resultado += cita.toString();
		}
		return resultado;
	}

	@Override
	public String toString() {
		return  "RUT: " + RUT + "\n" +
				"Nombre: " + nombre + "\n" +
				"Historial: " + historial + "\n" +
				"Edad: " + edad + "\n" +
				"EnfermedadCronica: " + enfermedadCronica + "\n" +
				"MedicoTratante: " + medicoTratante + "\n";
	}

	public String getNombre() {
		return nombre;
	}

	public String getRUT() {
		return RUT;
	}

	public int getEdad() {
		return edad;
	}

	public String getEnfermedadCronica() {
		return enfermedadCronica;
	}

	public PersonalMedico getMedicoTratante() {
		return medicoTratante;
	}

	public boolean contrasenaCorrecta(String contrasena) {
		return this.contrasena.equals(contrasena);
	}

	public boolean cambiarContrasena(String contrasena) throws ExecutionException, InterruptedException {
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> query = db.collection("Pacientes").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			if (this.RUT.equals(document.getString("RUT"))) {
				if (!document.getString("contrasena").equals(contrasena)) {
					document.getReference().update("contrasena", contrasena);
					System.out.println("contraseña actualizada!");
					return true;
				}
			}
		}
		return false;
	}

	public static void registarPaciente(String RUT, String nombre, String contrasena, int edad) throws ExecutionException, InterruptedException {
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();
		Map<String,Object> data = new HashMap<>();
		data.put("RUT",RUT);
		data.put("nombre",nombre);
		data.put("contrasena",contrasena);
		data.put("edad",edad);
		data.put("enfermedadCronica","");
		data.put("historial","");
		data.put("medicoTratante","");

		DocumentReference docRef = db.collection("Pacientes").document();
		ApiFuture<WriteResult> result = docRef.set(data);
		System.out.println("Tiempo de actualizacion: " + result.get().getUpdateTime());
	}
}