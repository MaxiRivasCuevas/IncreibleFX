package org.example.gestionclinica.RRHH;

import java.util.concurrent.ExecutionException;

public interface PersonalInterno {

	/**
	 * 
	 * @param n
	 */
	boolean tomarVacaciones(int n);

	/**
	 * 
	 * @param nivelAcceso
	 */
	int inscribir(int nivelAcceso);

	boolean contrasenaCorrecta(String contrasena);

	boolean cambiarContrasena(String contrasena) throws ExecutionException, InterruptedException;

	void calcularVacaciones();
}