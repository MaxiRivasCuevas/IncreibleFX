package org.example.gestionclinica;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.gestionclinica.RRHH.PersonalMedico;
import org.example.gestionclinica.clientes.Cita;
import org.example.gestionclinica.clientes.Paciente;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import static org.example.gestionclinica.Clinica.inicializarFirebase;

public class AgregarCitaController implements Initializable {
    @FXML
    private Button buttonAgregar;

    @FXML
    private TextField tfIdPaciente;

    @FXML
    private TextField tfIdMedico;

    @FXML
    private ComboBox<String> cbDia;

    @FXML
    private ComboBox<String> cbMes;

    @FXML
    private ComboBox<String> cbAnio;

    @FXML
    private ComboBox<String> cbHora;

    @FXML
    private ComboBox<String> cbMinuto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbDia.setItems(FXCollections.observableArrayList("01","02", "03","04","05","06","07","08","09","10","11","12","13","14","15",
                "16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"));
        cbMes.setItems(FXCollections.observableArrayList("01","02", "03","04","05","06","07","08","09","10","11","12"));
        cbAnio.setItems(FXCollections.observableArrayList("2025","2026","2027","2028","2029","2030"));

        cbHora.setItems(FXCollections.observableArrayList("00","01","02", "03","04","05","06","07","08","09","10","11","12","13","14","15",
                "16","17","18","19","20","21","22","23"));
        cbMinuto.setItems(FXCollections.observableArrayList("00","10", "20","30","40","50","60"));
        buttonAgregar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tfIdPaciente.getText().isEmpty() && !tfIdMedico.getText().isEmpty() && cbDia.getValue() != null && cbMes.getValue() != null && cbAnio.getValue()!= null && cbHora.getValue() != null && cbMinuto.getValue() != null) {
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
                    PersonalMedico personalMedicoActual = null;
                    for (PersonalMedico personalMedico: medicos){
                        if (personalMedico.getNombre().equals(tfIdMedico.getText()) || personalMedico.getIDFuncionario().equals(tfIdMedico.getText())){
                            personalMedicoActual = personalMedico;
                        }
                    }
                    for (Paciente paciente: pacientes){
                        if (paciente.getNombre().equals(tfIdPaciente.getText()) || paciente.getRUT().equals(tfIdPaciente.getText())){
                            pacienteActual = paciente;
                        }
                    }
                    if (personalMedicoActual != null && pacienteActual != null) {
                        String fechaActual = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                        String [] fechaActualDesgolsada = fechaActual.split("-");
                        int diaActual = Integer.parseInt(fechaActualDesgolsada[0]);
                        int mesActual = Integer.parseInt(fechaActualDesgolsada[1]);
                        int anioActual = Integer.parseInt(fechaActualDesgolsada[2]);
                        boolean agregarCita = false;

                        if (anioActual < Integer.parseInt(cbAnio.getValue())) {
                            agregarCita = true;
                        } else if (anioActual == Integer.parseInt(cbAnio.getValue())){
                            if (mesActual < Integer.parseInt(cbMes.getValue())) {
                                agregarCita = true;
                            } else if (mesActual == Integer.parseInt(cbMes.getValue())){
                                if (diaActual <= Integer.parseInt(cbDia.getValue())) {
                                    agregarCita = true;
                                }
                            }
                        }

                        if (agregarCita) {
                            try {
                                String fecha = cbDia.getValue() + "-" + cbMes.getValue() + "-" + cbAnio.getValue();
                                String hora = cbHora.getValue() + ":" + cbMinuto.getValue();
                                Cita.registrarCita(fecha, hora, pacienteActual.getNombre(), personalMedicoActual.getNombre());
                                Stage stage = (Stage) buttonAgregar.getScene().getWindow();
                                stage.close();
                            } catch (ExecutionException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            System.out.println("No pueden ingresarse fechas pasadas!");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("No pueden ingresarse fechas pasadas!");
                            alert.show();
                        }
                    } else if (pacienteActual == null) {
                        System.out.println("Paciente no encontrado!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Paciente no encontrado!");
                        alert.show();
                    } else if (personalMedicoActual == null) {
                        System.out.println("Medico no encontrado!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Medico no encontrado!");
                        alert.show();
                    }
                } else {
                    System.out.println("Faltan Datos!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Faltan Datos!");
                    alert.show();
                }
            }
        });
    }
}
