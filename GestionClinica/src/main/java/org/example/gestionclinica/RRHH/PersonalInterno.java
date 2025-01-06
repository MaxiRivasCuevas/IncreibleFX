package org.example.gestionclinica.RRHH;

import java.util.concurrent.ExecutionException;

public interface PersonalInterno {

	boolean tomarVacaciones(int n);

	boolean contrasenaCorrecta(String contrasena);

	boolean cambiarContrasena(String contrasena) throws ExecutionException, InterruptedException;

	void calcularVacaciones();
}