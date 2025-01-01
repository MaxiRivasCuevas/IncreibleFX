package org.example.gestionclinica;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class AgregarEmpleado implements Initializable {
    @FXML
    private TextField tfIdEmpleado;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfContrasena;

    @FXML
    private TextField tfSueldo;

    @FXML
    private TextField tfRol;

    @FXML
    private TextField tfNivelAcceso;

    @FXML
    private TextField tfEspecialidad;

    @FXML
    private TextField tfInfoContacto;

    @FXML
    private ComboBox<String> textInput;

    @FXML
    private Button buttonContratar;

    @FXML
    private Button buttonVolver;

    private String usuario;
    int nivelAcceso;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textInput.setItems(FXCollections.observableArrayList("Administrativo","Medico", "no Medico Interno","no Medico Externo"));

        buttonVolver.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Clinica.cambioEscenaPA(event,"SesionPA.fxml","Bienvenido",usuario,nivelAcceso);
            }
        });

        buttonContratar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                boolean datosSuficientes = false;
                if (getInfoCB(event) != null) {
                    if (getInfoCB(event).equals("Administrativo") || getInfoCB(event).equals("no Medico Interno")) {
                        if (!tfIdEmpleado.getText().isEmpty() && !tfNombre.getText().isEmpty() && !tfContrasena.getText().isEmpty()) {
                            if (!tfSueldo.getText().isEmpty() && !tfRol.getText().isEmpty() && !tfNivelAcceso.getText().isEmpty()) {
                                datosSuficientes = true;
                            }
                        }
                    } else if (getInfoCB(event).equals("Medico")) {
                        if (!tfIdEmpleado.getText().isEmpty() && !tfNombre.getText().isEmpty() && !tfContrasena.getText().isEmpty()) {
                            if (!tfSueldo.getText().isEmpty() && !tfRol.getText().isEmpty() && !tfNivelAcceso.getText().isEmpty()) {
                                if (!tfEspecialidad.getText().isEmpty()) {
                                    datosSuficientes = true;
                                }
                            }
                        }
                    } else if (getInfoCB(event).equals("no Medico Externo")) {
                        if (!tfIdEmpleado.getText().isEmpty() && !tfNombre.getText().isEmpty() && !tfContrasena.getText().isEmpty()) {
                            if (!tfSueldo.getText().isEmpty() && !tfRol.getText().isEmpty() && !tfNivelAcceso.getText().isEmpty()) {
                                if (!tfEspecialidad.getText().isEmpty() && !tfInfoContacto.getText().isEmpty()) {
                                    datosSuficientes = true;
                                }
                            }
                        }
                    }
                }

                boolean datosValidos = false;
                if (datosSuficientes) {
                    if (esInt(tfSueldo.getText()) && esInt(tfNivelAcceso.getText())) {
                        datosValidos = true;
                    }
                }

                if (datosSuficientes && datosValidos) {
                    try {
                        Clinica.subirEmpleadoAFirebase(tfIdEmpleado.getText(), tfNombre.getText(), tfContrasena.getText(), Integer.parseInt(tfSueldo.getText()), tfRol.getText(), Integer.parseInt(tfNivelAcceso.getText()), getInfoCB(event), tfEspecialidad.getText(), tfInfoContacto.getText());
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Clinica.cambioEscenaPA(event, "SesionPA.fxml", "Bienvenido", usuario, nivelAcceso);
                } else {
                    if (!datosSuficientes) {
                        System.out.println("Faltan datos para este tipo de empleado!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Faltan datos para este tipo de empleado!");
                        alert.show();
                    } else {
                        System.out.println("En sueldo y nivel de acceso solo ingrese numeros enteros!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("En sueldo y nivel de acceso solo ingrese numeros enteros!");
                        alert.show();
                    }
                }
            }
        });
    }

    @FXML
    String getInfoCB(ActionEvent event) {
        return textInput.getValue();
    }

    public void setInfo(String usuario,int nivelAcceso){
        this.usuario = usuario;
        this.nivelAcceso = nivelAcceso;
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