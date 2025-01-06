package org.example.gestionclinica.clientes;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import org.example.gestionclinica.RRHH.PersonalMedico;
import java.util.HashMap;
import java.util.List;
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
		return  "-Fecha: " + fecha + "\n" +
				" Hora: " + hora + "\n" +
				" Medico: " + medico.getNombre() + "\n";
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

	public void eliminarCita() throws ExecutionException, InterruptedException {
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();

		ApiFuture<QuerySnapshot> query = db.collection("Citas").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			if (this.getPaciente().getNombre().equals(document.getString("Paciente")) && this.getMedico().getNombre().equals(document.getString("Medico"))) {
				if (this.fecha.equals(document.getString("fecha"))&&this.hora.equals(document.getString("hora"))) {
					document.getReference().delete();
					System.out.println("cita eliminada");
				}
			}
		}
	}
}