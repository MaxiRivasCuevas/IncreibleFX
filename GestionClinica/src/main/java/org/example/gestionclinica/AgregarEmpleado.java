package org.example.gestionclinica;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextField;
import org.example.gestionclinica.RRHH.Funcionario;
import org.example.gestionclinica.clientes.Paciente;

import java.net.URL;
import java.util.ArrayList;
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
                try {
                    Clinica.subirEmpleadoAFirebase(tfIdEmpleado.getText(),tfNombre.getText(),tfContrasena.getText(),Integer.parseInt(tfSueldo.getText()),tfRol.getText(),Integer.parseInt(tfNivelAcceso.getText()),getInfoCB(event),tfEspecialidad.getText(), tfInfoContacto.getText());
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Clinica.cambioEscenaPA(event,"SesionPA.fxml","Bienvenido",usuario,nivelAcceso);
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
}