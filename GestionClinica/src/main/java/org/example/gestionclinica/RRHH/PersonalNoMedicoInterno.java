package org.example.gestionclinica.RRHH;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import org.example.gestionclinica.clientes.Paciente;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.example.gestionclinica.Clinica.inicializarFirebase;

public class PersonalNoMedicoInterno extends Funcionario implements PersonalInterno {
	private String contrasena;
	private int nivelAcceso;
	private int vacaciones;

	public PersonalNoMedicoInterno(String IDFuncionario, String nombre, String contrasena, String historial, int sueldoBruto, String fechaContratacion, String rol, int nivelAcceso, int vacaciones) {
		super(IDFuncionario, nombre, historial, sueldoBruto, fechaContratacion, rol);
		this.contrasena = contrasena;
		this.nivelAcceso = nivelAcceso;
		this.vacaciones = vacaciones;
	}

	public void inscribirPaciente(int nivelAcceso) {
		// TODO - implement PersonalNoMedicoInterno.inscribirPaciente
		throw new UnsupportedOperationException();
	}

	public void inscribirCita(int nivelAcceso, Paciente paciente) {

	}

	@Override
	public int tomarVacaciones(int n) {
		return 0;
	}

	@Override
	public int inscribir(int nivelAcceso) {
		return 0;
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

		ApiFuture<QuerySnapshot> query = db.collection("PersonalNoMedicoInterno").get();
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

		ApiFuture<QuerySnapshot> query = db.collection("PersonalNoMedicoInterno").get();
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
}