package org.example.gestionclinica;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.api.core.ApiFuture;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
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

		ArrayList<Paciente> pacientes = cargarDatosPacientes(db);

		for (Paciente persona : pacientes) {
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

	public static ArrayList<Paciente> cargarDatosPacientes(Firestore db) throws ExecutionException, InterruptedException {
		ArrayList<Paciente> pacientes = new ArrayList<>();

		ApiFuture<QuerySnapshot> query =db.collection("Pacientes").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents){
			pacientes.add(new Paciente(document.getString("RUT")
					, document.getString("nombre"), document.getString("contrasena")
					, document.getString("historial")
					, doubleToInt(document.getDouble("edad"))
					, document.getString("enfermedadCronica")));
		}
		return pacientes;
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
					, document.getString("nombre"), document.getString("contrasena")
					, document.getString("historial"), doubleToInt(document.getDouble("sueldoBruto"))
					, document.getString("fechaContratacion"), document.getString("rol")
					, doubleToInt(document.getDouble("nivelAcceso")), document.getString("especialidad")
					, doubleToInt(document.getDouble("vacaciones"))));
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
					, document.getString("nombre"), document.getString("contrasena")
					, document.getString("historial"), doubleToInt(document.getDouble("sueldoBruto"))
					, document.getString("fechaContratacion"), document.getString("rol")
					, doubleToInt(document.getDouble("nivelAcceso")), doubleToInt(document.getDouble("vacaciones"))));
		}
		return funcionarios;
	}

	public static String crearArregloFuncionarios(ArrayList<Funcionario> funcionarios){
		String resultado;
		if (funcionarios.isEmpty()){
			resultado = null;
		}else{
			resultado = funcionarios.get(0).getNombre() + "\n";
		}
		for (int i = 1; i < funcionarios.size(); i++) {
			resultado += funcionarios.get(i).getNombre() + "\n";
		}
		return resultado;
	}

	public static void cambioEscena(ActionEvent event, String fxmlFile, String title, String usuario, ArrayList<Funcionario> funcionarios, ArrayList<Paciente> pacientes){
		Parent root = null;
		if (usuario != null){
			try {
				FXMLLoader loader = new FXMLLoader(Clinica.class.getResource(fxmlFile));
				root = loader.load();
				SesionPersonalAdmin sesionPersonalAdmin = loader.getController();
				String nombresFuncionarios = crearArregloFuncionarios(funcionarios);
				sesionPersonalAdmin.setInfo(usuario, nombresFuncionarios);
				System.out.println(nombresFuncionarios);
			} catch (Exception e){
				e.printStackTrace();
			}
		} else {
			try {
				root = FXMLLoader.load(Clinica.class.getResource(fxmlFile));
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle(title);
		stage.setScene(new Scene(root, 600,400));
		stage.show();
	}

	public static void IniciarSesion(ActionEvent event, String usuario, String contrasena) throws ExecutionException, InterruptedException {
		inicializarFirebase();
		Firestore db = FirestoreClient.getFirestore();
		ArrayList<Funcionario> funcionarios = cargarDatosPersonal(db);
		ArrayList<Paciente> pacientes = cargarDatosPacientes(db);
		boolean usuarioValido = false;
		for (Funcionario funcionario : funcionarios) {
			if (funcionario.getNombre().equals(usuario) && ((PersonalAdmin) funcionario).contrasenaCorrecta(contrasena)) {
				usuarioValido = true;
			}
		}
		if (usuarioValido) {
			cambioEscena(event, "SesionPA.fxml", "Bienvenido!", usuario, funcionarios, pacientes);
		} else {
			System.out.println("Usuario o Contraseña incorrecta");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Usuario o Contraseña incorrecta!");
			alert.show();
		}
	}

	public static void IngresarNuevoUsuario(){}
}