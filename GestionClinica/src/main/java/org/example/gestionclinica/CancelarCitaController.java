package org.example.gestionclinica;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.gestionclinica.RRHH.PersonalMedico;
import org.example.gestionclinica.clientes.Cita;
import org.example.gestionclinica.clientes.Paciente;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import static org.example.gestionclinica.Clinica.inicializarFirebase;

public class CancelarCitaController implements Initializable {
    @FXML
    private Button buttonBuscar;

    @FXML
    private Button buttonEliminar;

    @FXML
    private Label lablePaciente;

    @FXML
    private Label lableCitas;

    @FXML
    private TextField tfNombrePaciente;

    @FXML
    private TextField tfNumCita;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonBuscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                if (!tfNombrePaciente.getText().isEmpty() || !lablePaciente.getText().equals("Ninguno")){
                    if (FirebaseApp.getApps().isEmpty()) {
                        inicializarFirebase();
                    }
                    Firestore db = FirestoreClient.getFirestore();
                    ArrayList<PersonalMedico> medicos = new ArrayList<>();
                    ArrayList<Paciente> pacientes = new ArrayList<>();
                    try {
                        medicos = Clinica.funcionariosQueSonMedicos(Clinica.cargarDatosPersonalMedico(db));
                        pacientes = Clinica.cargarDatosPacientes(db, medicos);
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }

                    Paciente pacienteActual = null;
                    for (Paciente paciente: pacientes){
                        if (paciente.getNombre().equals(tfNombrePaciente.getText()) || paciente.getRUT().equals(tfNombrePaciente.getText())){
                            pacienteActual = paciente;
                            lablePaciente.setText(paciente.getNombre());
                        }
                    }
                    if (pacienteActual == null){
                        for (Paciente paciente: pacientes){
                            if (paciente.getNombre().equals(lablePaciente.getText()) || paciente.getRUT().equals(lablePaciente.getText())){
                                pacienteActual = paciente;
                            }
                        }
                    }
                    if (pacienteActual != null){
                        ArrayList<Cita> citas = null;
                        try {
                            citas = Clinica.cargarCitas(db,medicos,pacientes);
                        } catch (ExecutionException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        String citasString = "";
                        int n = 0;
                        for (Cita cita : citas){
                            if (cita.getPaciente().getNombre().equals(pacienteActual.getNombre())){
                                citasString += "N°" + n + " Dia: " + cita.getFecha() + " a las: " + cita.getHora() + "\n"
                                             + "    con " +cita.getMedico().getNombre()+"\n";
                                n++;
                            }
                        }
                        lableCitas.setText(citasString);
                    } else {
                        System.out.println("Paciente no encontrado!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Paciente no encontrado!");
                        alert.show();
                    }

                } else {
                    System.out.println("Ingrese un paciente!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ingrese un paciente!");
                    alert.show();
                }
            }
        });

        buttonEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                if (!tfNumCita.getText().isEmpty()){
                    if(esInt(tfNumCita.getText())){
                        if (FirebaseApp.getApps().isEmpty()) {
                            inicializarFirebase();
                        }
                        Firestore db = FirestoreClient.getFirestore();
                        ArrayList<PersonalMedico> medicos = new ArrayList<>();
                        ArrayList<Paciente> pacientes = new ArrayList<>();
                        ArrayList<Cita> citas = null;
                        try {
                            medicos = Clinica.funcionariosQueSonMedicos(Clinica.cargarDatosPersonalMedico(db));
                            pacientes = Clinica.cargarDatosPacientes(db, medicos);
                            citas = Clinica.cargarCitas(db,medicos,pacientes);
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }

                        int i = 0;
                        Cita citaActual = null;
                        for (Cita cita : citas){
                            if (cita.getPaciente().getNombre().equals(lablePaciente.getText())){
                                i++;
                                if (i == Integer.parseInt(tfNumCita.getText())+1) {
                                    citaActual = cita;
                                }
                            }
                        }
                        if (citaActual == null) {
                            System.out.println("No existe una cita con ese numero");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("No existe una cita con ese numero!");
                            alert.show();
                        } else {
                            try {
                                citaActual.eliminarCita();
                                Stage stage = (Stage) buttonEliminar.getScene().getWindow();
                                stage.close();
                            } catch (ExecutionException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    } else {
                        System.out.println("Ingrese un numero entero!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Ingrese un numero entero!");
                        alert.show();
                    }
                } else {
                    System.out.println("Ingrese el numero de la cita!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ingrese el numero de la cita!");
                    alert.show();
                }
            }
        });
    }
    private boolean esInt(String n) {
        int i = 0;
        try {
            i = Integer.parseInt(n);
            return true;
        } catch (Exception InputMismatchException) {
            System.out.println("Ingrese solo un numero!!!");
        }
        return false;
    }
}