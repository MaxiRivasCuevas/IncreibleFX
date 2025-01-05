package org.example.gestionclinica.RRHH;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import javafx.scene.control.Alert;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	public boolean tomarVacaciones(int n) {
		if (vacaciones >= n) {
			super.agregarEntradaAHistorial("Tomo vacaciones por: " + String.valueOf(vacaciones));
			if (FirebaseApp.getApps().isEmpty()) {
				inicializarFirebase();
			}
			Firestore db = FirestoreClient.getFirestore();

			ApiFuture<QuerySnapshot> query = db.collection("PersonalMedico").get();
			QuerySnapshot querySnapshot = null;
			try {
				querySnapshot = query.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new RuntimeException(e);
			}
			List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				if (this.getIDFuncionario().equals(document.getString("IdFuncionario"))) {
					document.getReference().update("historial", super.getHistorial());
					System.out.println("vacaciones registradas");
				}
			}
			return true;
		}
		return false;
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

	@Override
	public boolean cambiarContrasena(String contrasena) throws ExecutionException, InterruptedException {
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<QuerySnapshot> query = db.collection("PersonalMedico").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			if (this.getIDFuncionario().equals(document.getString("IdFuncionario"))) {
				if (!document.getString("constrasena").equals(contrasena)) {
					document.getReference().update("contrasena", contrasena);
					System.out.println("contraseña actualizada!");
					return true;
				} else {
					System.out.println("La contraseña debe ser diferenete a la anterior!");
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("La contraseña debe ser diferenete a la anterior!");
					alert.show();
				}
			}
		}
		return false;
	}

	@Override
	public void calcularVacaciones() {
		int n = 0;
		String [] fechaInicio = super.getFechaContratacion().split("-");
		int diaInicio = Integer.parseInt(fechaInicio[0]);
		int mesInicio = Integer.parseInt(fechaInicio[1]);
		int anioInicio = Integer.parseInt(fechaInicio[2]);

		String fechaActual = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String [] fechaActualDesgolsada = fechaActual.split("-");
		int diaActual = Integer.parseInt(fechaActualDesgolsada[0]);
		int mesActual = Integer.parseInt(fechaActualDesgolsada[1]);
		int anioActual = Integer.parseInt(fechaActualDesgolsada[2]);

		if ((anioActual - anioInicio) > 0){
			n += (anioActual-anioInicio) * 15 ;
		}
		this.vacaciones = n;
	}
}