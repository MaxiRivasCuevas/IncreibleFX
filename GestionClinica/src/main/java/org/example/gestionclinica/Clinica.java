package org.example.gestionclinica;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.api.core.ApiFuture;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.example.gestionclinica.RRHH.Funcionario;
import org.example.gestionclinica.RRHH.PersonalAdmin;
import org.example.gestionclinica.RRHH.PersonalMedico;
import org.example.gestionclinica.clientes.Paciente;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Clinica {
	private String nombre;

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		inicializarFirebase();
		Firestore db = FirestoreClient.getFirestore();
		System.out.println("Coneccion a base de Datos Exitosa!");

		ArrayList<Funcionario> funcionarios = cargarDatosPersonal(db);

		for (Funcionario persona : funcionarios) {
			System.out.println(persona.toString());
		}
/*
		String [][] arregloInicioSesion = crearArregloInicioSesion(funcionarios);

		System.out.println("\n");
		for (String [] sesion : arregloInicioSesion) {
			System.out.println(sesion[0]);
			System.out.println(sesion[1]);
			System.out.println(sesion[2]);
		}
*/
		ArrayList<Paciente> pacientes = new ArrayList<>();
	}

	public String infoRAW(String nombre) throws ExecutionException, InterruptedException {
		String infoRAW = "";
		inicializarFirebase();
		Firestore db = FirestoreClient.getFirestore();
		ArrayList<Funcionario> funcionarios = cargarDatosPersonal(db);

		for (Funcionario persona : funcionarios) {
			if (persona.getNombre().equals(nombre)) {
				infoRAW = persona.toString();
			}
		}
		return infoRAW;
	}

	public static void inicializarFirebase(){
		try{
			FileInputStream json = new FileInputStream("increible-f0788-firebase-adminsdk-9j0tz-fa58166625.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(json))
					.setDatabaseUrl("https://increible-f0788.nam5.firebaseio.com/")
					.build();

			FirebaseApp.initializeApp(options);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public static ArrayList<Funcionario> cargarDatosPersonal(Firestore db) throws ExecutionException, InterruptedException {
		ArrayList<Funcionario> funcionarios = new ArrayList<>();
		ArrayList<Funcionario> personalMedico = cargarDatosPersonalMedico(db);
		//agrega personal medico a la lista final
        funcionarios.addAll(personalMedico);
		ArrayList<Funcionario> personalAdmin = cargarDatosPersonalAdmin(db);
		funcionarios.addAll(personalAdmin);
		return funcionarios;
	}

	public static int doubleToInt(double n){
		int i = (int) n;
		return i;
	}

	public static ArrayList<Funcionario> cargarDatosPersonalMedico(Firestore db) throws InterruptedException, ExecutionException {
		ArrayList<Funcionario> funcionarios = new ArrayList<>();

		ApiFuture<QuerySnapshot> query =db.collection("PersonalMedico").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents){
			funcionarios.add(new PersonalMedico(document.getString("IdFuncionario")
					, document.getString("nombre"), document.getString("historial")
					, doubleToInt(document.getDouble("sueldoBruto")), document.getString("fechaContratacion")
					, document.getString("rol"), doubleToInt(document.getDouble("nivelAcceso"))
					, document.getString("especialidad"), doubleToInt(document.getDouble("vacaciones"))));
		}
		return funcionarios;
	}

	public static ArrayList<Funcionario> cargarDatosPersonalAdmin(Firestore db) throws InterruptedException, ExecutionException {
		ArrayList<Funcionario> funcionarios = new ArrayList<>();

		ApiFuture<QuerySnapshot> query =db.collection("PersonalAdmin").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents){
			funcionarios.add(new PersonalAdmin(document.getString("IdFuncionario")
					, document.getString("nombre"), document.getString("historial")
					, doubleToInt(document.getDouble("sueldoBruto")), document.getString("fechaContratacion")
					, document.getString("rol"), doubleToInt(document.getDouble("nivelAcceso"))
					, doubleToInt(document.getDouble("vacaciones"))));
		}
		return funcionarios;
	}

	public static String [][] crearArregloInicioSesion(ArrayList<Funcionario> funcionarios){
		//Arreglo de dos dimensiones, la primera es de cada funcionario
		//, la segunda es de nombre, id y nivel de acceso respectivamente
		String [][] arregloInicioSesion = new String[funcionarios.size()][3];

		for (int i = 0; i < funcionarios.size(); i++) {
			arregloInicioSesion[i][0] = funcionarios.get(i).getNombre();
			arregloInicioSesion[i][1] = funcionarios.get(i).getIDFuncionario();
			arregloInicioSesion[i][2] = String.valueOf(funcionarios.get(i).getNivelAcceso());
		}
		return arregloInicioSesion;
	}
}