package org.example.gestionclinica.clientes;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import org.example.gestionclinica.RRHH.PersonalMedico;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.example.gestionclinica.Clinica.inicializarFirebase;

public class Cita {
	private String fecha;
	private String hora;
	private Paciente paciente;
	private PersonalMedico medico;

	public Cita(String fecha, String hora, Paciente paciente, PersonalMedico medico) {
		this.fecha = fecha;
		this.hora = hora;
		this.paciente = paciente;
		this.medico = medico;
	}

	public void agregarInfoPaciente(int nivelAcceso, Paciente paciente) {
		if(nivelAcceso == 2) {

		}else{
			System.out.println("El nivel de acceso no es el requerido");
		}
	}

	public void agregarEnfermedadCronica(int nivelAcceso) {
		if(nivelAcceso == 2) {
			String info = null;
			//recibe la info
			this.paciente.agregarEnfermedadCronica(info);
		}else{
			System.out.println("El nivel de acceso no es el requerido");
		}
	}

	public void cambiarMedico(PersonalMedico medico, int nivelAcceso) {
		if(nivelAcceso == 2) {
			this.paciente.cambarMedicoTratante(medico);
		}else{
			System.out.println("El nivel de acceso no es el requerido");
		}
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public PersonalMedico getMedico() {
		return medico;
	}

	public String getFecha() {
		return fecha;
	}

	public String getHora() {
		return hora;
	}

	@Override
	public String toString() {
		return  "fecha: " + fecha + "\n" +
				"hora: " + hora + "\n" +
				"paciente: " + paciente.getNombre() + "\n" +
				"medico: " + medico.getNombre() + "\n";
	}

	public String detalleString(){
		return  "Fecha: " + fecha + "\n" +
				"Hora: " + hora + "\n";
	}

	public static void registrarCita(String fecha, String hora, String paciente, String medico) throws ExecutionException, InterruptedException {
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();
		Map<String,Object> data = new HashMap<>();
		data.put("fecha", fecha);
		data.put("hora", hora);
		data.put("Paciente", paciente);
		data.put("Medico", medico);
		DocumentReference docRef = db.collection("Citas").document();
		ApiFuture<WriteResult> result = docRef.set(data);
		System.out.println("Tiempo de actualizacion: " + result.get().getUpdateTime());
	}
}