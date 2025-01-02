package org.example.gestionclinica.RRHH;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import javafx.scene.control.Alert;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.example.gestionclinica.Clinica.inicializarFirebase;

public class PersonalAdmin extends Funcionario implements PersonalInterno {
	private String contrasena;
	private int nivelAcceso;
	private int vacaciones;

	public PersonalAdmin(String IDFuncionario, String nombre, String contrasena, String historial, int sueldoBruto, String fechaContratacion, String rol, int nivelAcceso, int vacaciones) {
		super(IDFuncionario, nombre, historial, sueldoBruto, fechaContratacion, rol);
		this.contrasena = contrasena;
		this.nivelAcceso = nivelAcceso;
		this.vacaciones = vacaciones;
	}

	public void despedir(Funcionario funcionario) {
		// TODO - implement PersonalAdmin.despedir
		throw new UnsupportedOperationException();
	}

	public void calcularFiniquito(String historial) {
		// TODO - implement PersonalAdmin.calcularFiniquito
		throw new UnsupportedOperationException();
	}

	public void despedir(Funcionario funcionario, String infoEmpresa) {
		// TODO - implement PersonalAdmin.despedir
		throw new UnsupportedOperationException();
	}

	public void cambiarSueldo(Funcionario funcionario) {
		// TODO - implement PersonalAdmin.cambiarSueldo
		throw new UnsupportedOperationException();
	}

	public void contratar() {
		// TODO - implement PersonalAdmin.contratar
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

	@Override
	public String toString() {
		return super.toString() +
				"NivelAcceso: " + nivelAcceso + "\n" +
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

		ApiFuture<QuerySnapshot> query = db.collection("PersonalAdmin").get();
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

		ApiFuture<QuerySnapshot> query = db.collection("PersonalAdmin").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			if (this.getIDFuncionario().equals(document.getString("IdFuncionario"))) {
				document.getReference().update("rol", rol);
				System.out.println("rol actualizado");
			}
		}
	}

	@Override
	public boolean contrasenaCorrecta(String contrasena) {
		return this.contrasena.equals(contrasena);
	}

	@Override
	public boolean cambiarContrasena(String contrasena) throws ExecutionException, InterruptedException {
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> query = db.collection("PersonalAdmin").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			if (this.getIDFuncionario().equals(document.getString("IdFuncionario"))) {
				if (!document.getString("contrasena").equals(contrasena)) {
					document.getReference().update("contrasena", contrasena);
					System.out.println("contraseña actualizada!");
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
}