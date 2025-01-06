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

public class PersonalNoMedicoExterno extends Funcionario {

	private String empresa;
	private String infoContactoEmpresa;

	public PersonalNoMedicoExterno(String IDFuncionario, String nombre, String historial, int sueldoBruto, String fechaContratacion, String rol, String empresa, String infoContactoEmpresa) {
		super(IDFuncionario, nombre, historial, sueldoBruto, fechaContratacion, rol);
		this.empresa = empresa;
		this.infoContactoEmpresa = infoContactoEmpresa;
	}

	public int getNivelAcceso() {
		return 10;
	}

	@Override
	public void actualizarSueldoBruto(int sueldoBruto) throws ExecutionException, InterruptedException {
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();

		ApiFuture<QuerySnapshot> query = db.collection("PersonalNoMedicoExterno").get();
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

		ApiFuture<QuerySnapshot> query = db.collection("PersonalNoMedicoExterno").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			if (this.getIDFuncionario().equals(document.getString("IdFuncionario"))) {
				document.getReference().update("rol", rol);
				System.out.println("rol actualizado");
			}
		}
	}
}