package org.example.gestionclinica.RRHH;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.example.gestionclinica.Clinica.inicializarFirebase;

public class PersonalMedico extends Funcionario implements PersonalInterno {
	private String contrasena;
	private int nivelAcceso;
	private String especialidad;
	private int vacaciones;

	public PersonalMedico(String IDFuncionario, String nombre, String contrasena, String historial, int sueldoBruto, String fechaContratacion, String rol, int nivelAcceso, String especialidad, int vacaciones) {
		super(IDFuncionario, nombre, historial, sueldoBruto, fechaContratacion, rol);
		this.contrasena = contrasena;
		this.nivelAcceso = nivelAcceso;
		this.especialidad = especialidad;
		this.vacaciones = vacaciones;
	}

	public void cambiarEspecialidad() {
		// TODO - implement PersonalMedico.cambiarEspecialidad
		throw new UnsupportedOperationException();
	}

	@Override
	public int tomarVacaciones(int n) {
		return 0;
	}

	@Override
	public int inscribir(int nivelAcceso) {
		return 0;
	}

	public String toString() {
		return super.toString() +
				"NivelAcceso: " + nivelAcceso + "\n" +
				"Especialidad: " + especialidad + "\n" +
				"Vacaciones: " + vacaciones + "\n";
	}

	public int getNivelAcceso() {
		return nivelAcceso;
	}

	@Override
	public void actualizarSueldoBruto(int sueldoBruto) throws ExecutionException, InterruptedException {
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();

		ApiFuture<QuerySnapshot> query = db.collection("PersonalMedico").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			if (this.getIDFuncionario().equals(document.getString("IdFuncionario"))) {
				document.getReference().update("sueldoBruto", sueldoBruto);
				System.out.println("sueldo actualizado");
			}
		}
	}

	@Override
	public void actualizarRol(String rol) throws ExecutionException, InterruptedException {
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();

		ApiFuture<QuerySnapshot> query = db.collection("PersonalMedico").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			if (this.getIDFuncionario().equals(document.getString("IdFuncionario"))) {
				document.getReference().update("rol", rol);
				System.out.println("rol actualizado");
			}
		}
	}

	public int getVacaciones() {
		return vacaciones;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	@Override
	public boolean contrasenaCorrecta(String contrasena) {
		return this.contrasena.equals(contrasena);
	}
}