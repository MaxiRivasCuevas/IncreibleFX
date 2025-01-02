package org.example.gestionclinica;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.gestionclinica.RRHH.Funcionario;
import org.example.gestionclinica.RRHH.PersonalAdmin;
import org.example.gestionclinica.RRHH.PersonalMedico;
import org.example.gestionclinica.RRHH.PersonalNoMedicoInterno;
import org.example.gestionclinica.clientes.Paciente;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class CambioContraSenaController implements Initializable {
    @FXML
    private Button buttonActualizar;

    @FXML
    private TextField tfContrasenaNueva;

    @FXML
    private TextField tfContrasenaRepetida;

    private Funcionario funcionario;
    private Paciente paciente;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonActualizar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                boolean operacionExitosa = false;
                if (!tfContrasenaNueva.getText().equals("") && !tfContrasenaRepetida.getText().equals("")) {
                    if (tfContrasenaNueva.getText().equals(tfContrasenaRepetida.getText())) {
                        if (funcionario != null) {
                            if (funcionario.getClass() == PersonalMedico.class){
                                PersonalMedico personalMedico = (PersonalMedico) funcionario;
                                if (!personalMedico.contrasenaCorrecta(tfContrasenaNueva.getText())) {
                                    try {
                                        operacionExitosa = personalMedico.cambiarContrasena(tfContrasenaNueva.getText());
                                    } catch (ExecutionException | InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                            if (funcionario.getClass() == PersonalAdmin.class){
                                PersonalAdmin personalAdmin = (PersonalAdmin) funcionario;
                                if (!personalAdmin.contrasenaCorrecta(tfContrasenaNueva.getText())) {
                                    try {
                                        operacionExitosa = personalAdmin.cambiarContrasena(tfContrasenaNueva.getText());
                                    } catch (ExecutionException | InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                            if (funcionario.getClass() == PersonalNoMedicoInterno.class){
                                PersonalNoMedicoInterno personalNoMedicoInterno = (PersonalNoMedicoInterno) funcionario;
                                if (!personalNoMedicoInterno.contrasenaCorrecta(tfContrasenaNueva.getText())) {
                                    try {
                                        operacionExitosa = personalNoMedicoInterno.cambiarContrasena(tfContrasenaNueva.getText());
                                    } catch (ExecutionException | InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        } else {
                            try {
                                operacionExitosa = paciente.cambiarContrasena(tfContrasenaNueva.getText());
                            } catch (ExecutionException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        System.out.println("La contraseña a confirmar debe ser igual a la ingresada!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("La contraseña a confirmar debe ser igual a la ingresada!");
                        alert.show();
                    }
                } else {
                    System.out.println("Ingrese la nueva contraseña!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ingrese la nueva contraseña!");
                    alert.show();
                }
                if (operacionExitosa) {
                    Stage stage = (Stage) buttonActualizar.getScene().getWindow();
                    stage.close();
                }
            }
        });
    }

    public void setInfo(Funcionario funcionario) {
        this.funcionario = funcionario;
        this.paciente = null;
    }

    public void setInfo(Paciente paciente) {
        this.paciente = paciente;
        this.funcionario = null;
    }
}
