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
import org.example.gestionclinica.clientes.Cita;
import org.example.gestionclinica.clientes.Paciente;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Clinica {
	private String nombre;

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

	public static ArrayList<Paciente> cargarDatosPacientes(Firestore db,ArrayList<PersonalMedico> medicos) throws ExecutionException, InterruptedException {
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
		ArrayList<Cita> citas = cargarCitas(db, medicos, pacientes);
		for (Paciente paciente : pacientes){
			for (Cita cita : citas){
				if (cita.getPaciente().getNombre().equals(paciente.getNombre())){
					paciente.agregarCita(cita);
				}
			}
		}
		return pacientes;
	}

	public static ArrayList<Cita> cargarCitas(Firestore db, ArrayList<PersonalMedico> medicos, ArrayList<Paciente> pacientes) throws ExecutionException, InterruptedException {
		ArrayList<Cita> citas = new ArrayList<>();
		ApiFuture<QuerySnapshot> query =db.collection("Citas").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents){
			citas.add(new Cita(document.getString("fecha")
					, document.getString("hora")
					, encontrarPaciente(document.getString("Paciente"),pacientes)
					, encontrarMedico(document.getString("Medico"), medicos)));
		}
		return  citas;
	}

	public static PersonalMedico encontrarMedico(String nombre, ArrayList<PersonalMedico> medicos){
		PersonalMedico medico = null;
		for (PersonalMedico actual : medicos){
			if (actual.getNombre().equals(nombre)){
				medico = actual;
			}
		}
		return medico;
	}

	public static Paciente encontrarPaciente(String nombre, ArrayList<Paciente> pacientes){
		Paciente paciente = null;
		for (Paciente actual : pacientes){
			if (actual.getNombre().equals(nombre)){
				paciente = actual;
			}
		}
		return paciente;
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
					, document.getString("nombre"), document.getString("constrasena")
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

	public static void cambioEscenaPA(ActionEvent event, String fxmlFile, String title, String usuario, ArrayList<Funcionario> funcionarios, ArrayList<Paciente> pacientes, int nivelAcceso){
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

	public static void cambioEscenaPM(ActionEvent event, String fxmlFile, String title, String usuario, Firestore db, ArrayList<Funcionario> funcionarios, ArrayList<Paciente> pacientes, int nivelAcceso){
		Parent root = null;
		if (usuario != null){
			try {
				FXMLLoader loader = new FXMLLoader(Clinica.class.getResource(fxmlFile));
				root = loader.load();
				SesionPersonalMedico sesionPersonalMedico = loader.getController();
				ArrayList<Cita> citas = cargarCitas(db,funcionariosQueSonMedicos(funcionarios),pacientes);
				String citasString = "";
				int n = 0;
				for (Cita cita : citas){
					if (cita.getMedico().getNombre().equals(usuario)){
						citasString += "N°" + n + " A las: " + cita.getHora() + "\n"
								    + "    con " +cita.getPaciente().getNombre()+"\n";
						n++;
					}
				}
				String rol = null;
				String especialidad = null;
				for (PersonalMedico personal : funcionariosQueSonMedicos(funcionarios)) {
					if (personal.getNombre().equals(usuario)){
						rol = personal.getRol();
						especialidad = personal.getEspecialidad();
					}
				}
				sesionPersonalMedico.setInfo(usuario, citasString, rol, especialidad);
				System.out.println(citas);
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

	public static void cambioEscenaPaciente(ActionEvent event, String fxmlFile, String title, String usuario, ArrayList<Funcionario> funcionarios, ArrayList<Paciente> pacientes){
		Parent root = null;
		if (usuario != null){
			try {
				FXMLLoader loader = new FXMLLoader(Clinica.class.getResource(fxmlFile));
				root = loader.load();
				SesionPaciente sesionPaciente = loader.getController();
				String Rut = null;
				int edad = 0;
				String citas = null;
				String enfermedadesCronicas = null;
				String medicoTrante = null;
				for (Paciente paciente : pacientes) {
					if (paciente.getNombre().equals(usuario)) {
						Rut = paciente.getRUT();
						edad = paciente.getEdad();
						citas = paciente.citasToString();
						enfermedadesCronicas = paciente.getEnfermedadCronica();
						if (medicoTrante != null){medicoTrante = paciente.getMedicoTratante().getNombre();}
						else {medicoTrante = "Ninguno";}
					}
				}
				sesionPaciente.setInfo(usuario, Rut, edad, citas, enfermedadesCronicas, medicoTrante);
				System.out.println(citas);
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

	public static void cambioEscenaDetalleCita(ActionEvent event, String fxmlFile, String title, String usuario,String cita, String paciente) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(Clinica.class.getResource(fxmlFile));
			root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Detalles de Cita");
			stage.setScene(new Scene(root));
			DetalleUnaCita detalleUnaCita = loader.getController();
			detalleUnaCita.setInfo(cita, paciente);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void IniciarSesion(ActionEvent event, String usuario, String contrasena) throws ExecutionException, InterruptedException {
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();
		ArrayList<Funcionario> funcionarios = cargarDatosPersonal(db);
		ArrayList<Paciente> pacientes = cargarDatosPacientes(db, funcionariosQueSonMedicos(funcionarios));
		boolean usuarioValido = false;
		int nivelAcceso = 9;
		for (Funcionario funcionario : funcionarios) {
			if (funcionario.getClass() == PersonalAdmin.class) {
				if (funcionario.getNombre().equals(usuario) && ((PersonalAdmin) funcionario).contrasenaCorrecta(contrasena)) {
					usuarioValido = true;
					nivelAcceso = funcionario.getNivelAcceso();
				}
			} else if (funcionario.getClass() == PersonalMedico.class) {
				if (funcionario.getNombre().equals(usuario) && ((PersonalMedico) funcionario).contrasenaCorrecta(contrasena)) {
					usuarioValido = true;
					nivelAcceso = funcionario.getNivelAcceso();
				}
			}
		}
		for (Paciente paciente : pacientes) {
			if (paciente.getNombre().equals(usuario) && paciente.contrasenaCorrecta(contrasena)) {
				usuarioValido = true;
				nivelAcceso = 9;
			}
		}
		if (usuarioValido && nivelAcceso == 1) {
			cambioEscenaPA(event, "SesionPA.fxml", "Bienvenido!", usuario, funcionarios, pacientes, nivelAcceso);
		} else if (usuarioValido && nivelAcceso == 2) {
			cambioEscenaPM(event, "SesionPM.fxml", "Bienvenido!", usuario, db, funcionarios, pacientes,nivelAcceso);
		} else if (usuarioValido && nivelAcceso == 9) {
			cambioEscenaPaciente(event, "SesionPaciente.fxml", "Bienvenido!", usuario, funcionarios, pacientes);
		} else {
			System.out.println("Usuario o Contraseña incorrecta");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Usuario o Contraseña incorrecta!");
			alert.show();
		}
	}

	public static void IngresarNuevoUsuario(){}

	public static void verDetalleCita(ActionEvent event, String usuario,int n) throws ExecutionException, InterruptedException {
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();
		ArrayList<Funcionario> funcionarios = cargarDatosPersonalMedico(db);
		ArrayList<Paciente> pacientes = cargarDatosPacientes(db, funcionariosQueSonMedicos(funcionarios));
		ArrayList<Cita> citas = cargarCitas(db,funcionariosQueSonMedicos(funcionarios),pacientes);

		String infoCita = null;
		String infoPaciente = null;
		boolean citaExiste = false;
		int i = 0;
		for (Cita cita : citas){
			if (cita.getMedico().getNombre().equals(usuario)){
				i++;
				if (i == n+1) {
					infoCita = cita.detalleString();
					infoPaciente = cita.getPaciente().toString();
					citaExiste = true;
				}
			}
		}
		if (citaExiste) {
			cambioEscenaDetalleCita(event, "DUC.fxml","Detalle",usuario,infoCita,infoPaciente);
		}else {
			System.out.println("No existe una cita con ese numero");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("No existe una cita con ese numero!");
			alert.show();
		}
	}

	public static void detalleEmpleado(ActionEvent event, String usuario,String empleado) throws ExecutionException, InterruptedException {
		if (FirebaseApp.getApps().isEmpty()) {
			inicializarFirebase();
		}
		Firestore db = FirestoreClient.getFirestore();
		ArrayList<Funcionario> funcionarios = cargarDatosPersonal(db);
		Funcionario empleadoObjeto = null;
		boolean empleadoExiste = false;
		for (Funcionario funcionario : funcionarios) {
			if (funcionario.getNombre().equals(empleado)) {
				empleadoObjeto = funcionario;
				empleadoExiste = true;
			}
		}
		if (empleadoExiste) {
			cambioEscenaEmpleado(event, "DUE.fxml", "Informacion Empleado", usuario, empleadoObjeto);
		} else {
			System.out.println("No existe una empleado con ese nombre");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("No existe una empleado con ese nombre!");
			alert.show();
		}
	}

	public static void cambioEscenaEmpleado(ActionEvent event, String fxmlFile, String title, String usuario, Funcionario empleado){
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(Clinica.class.getResource(fxmlFile));
			root = loader.load();
			Stage stage = new Stage();
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			DetalleUnEmpleado detalleUnEmpleado = loader.getController();
			detalleUnEmpleado.setInfo(empleado.toString());
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<PersonalMedico> funcionariosQueSonMedicos(ArrayList<Funcionario> funcionarios){
		ArrayList<PersonalMedico> personalMedicos = new ArrayList<>();
		for (Funcionario funcionario : funcionarios) {
			if (funcionario.getClass() == PersonalMedico.class) {
				personalMedicos.add((PersonalMedico) funcionario);
			}
		}
		return personalMedicos;
	}
}