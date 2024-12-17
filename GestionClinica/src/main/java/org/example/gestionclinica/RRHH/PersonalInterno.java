package org.example.gestionclinica.RRHH;

public interface PersonalInterno {

	/**
	 * 
	 * @param n
	 */
	int tomarVacaciones(int n);

	/**
	 * 
	 * @param nivelAcceso
	 */
	int inscribir(int nivelAcceso);

	boolean contrasenaCorrecta(String contrasena);

}