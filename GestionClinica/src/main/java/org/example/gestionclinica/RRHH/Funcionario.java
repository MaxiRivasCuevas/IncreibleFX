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

public abstract class Funcionario {

	private String IDFuncionario;
	private String nombre;
	private String historial;
	private int sueldoBruto;
	private String fechaContratacion;
	private String rol;


	public Funcionario(String IDFuncionario, String nombre, String historial, int sueldoBruto, String fechaContratacion, String rol) {
		this.IDFuncionario = IDFuncionario;
		this.nombre = nombre;
		this.historial = historial;
		this.sueldoBruto = sueldoBruto;
		this.fechaContratacion = fechaContratacion;
		this.rol = rol;
	}

	public void felicitar() {
		String motivo = null;
		this.historial += motivo;
	}

	public String toString() {
		return  "Rut: " + IDFuncionario + "\n" +
				"Nombre: " + nombre + "\n" +
				"Historial: " + historial +
				"Sueldo Bruto: " + sueldoBruto + "\n" +
				"Fecha de Contratacion: " + fechaContratacion + "\n" +
				"Rol: " + rol + "\n";
	}

	public String getIDFuncionario() {
		return IDFuncionario;
	}

	public String getNombre() {
		return nombre;
	}

	public String getRol() {
		return rol;
	}

	public abstract int getNivelAcceso();

	public abstract void actualizarSueldoBruto(int sueldoBruto) throws ExecutionException, InterruptedException;

	public int getSueldoBruto() {
		return sueldoBruto;
	}

	public String getFechaContratacion() {
		return fechaContratacion;
	}

	public String getHistorial() {
		return historial;
	}

	public void agregarEntradaAHistorial(String entrada) {
		this.historial += entrada;
	}

	public void amonestar(String amonestacion){
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();

		this.historial += amonestacion;
		String [] tipo = {"PersonalAdmin", "PersonalMedico", "PersonalNoMedicoInterno", "PersonalNoMedicoExterno"};
		for (String collection : tipo) {
			ApiFuture<QuerySnapshot> query = db.collection(collection).get();
			QuerySnapshot querySnapshot = null;
			try {
				querySnapshot = query.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new RuntimeException(e);
			}
			List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				if (this.getIDFuncionario().equals(document.getString("IdFuncionario"))) {
					document.getReference().update("historial", this.historial);
					System.out.println("Amonestacion Realizada");
				}
			}
		}
	}

	public void felicitar(String amonestacion){
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();

		this.historial += amonestacion;
		String [] tipo = {"PersonalAdmin", "PersonalMedico", "PersonalNoMedicoInterno", "PersonalNoMedicoExterno"};
		for (String collection : tipo) {
			ApiFuture<QuerySnapshot> query = db.collection(collection).get();
			QuerySnapshot querySnapshot = null;
			try {
				querySnapshot = query.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new RuntimeException(e);
			}
			List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				if (this.getIDFuncionario().equals(document.getString("IdFuncionario"))) {
					document.getReference().update("historial", this.historial);
					System.out.println("Felicitacion Realizada");
				}
			}
		}
	}

	public abstract void actualizarRol(String rol) throws ExecutionException, InterruptedException;
}