package org.example.gestionclinica.RRHH;

public class PersonalMedico extends Funcionario implements PersonalInterno {
	private String contrasena;
	private int nivelAcceso;
	private String especialidad;
	private int vacaciones;

	public PersonalMedico(String IDFuncionario, String nombre, String contrasena, String historial, int sueldoBruto, String fechaContratacion, String rol, int nivelAcceso, String especialidad, int vacaciones) {
		super(IDFuncionario, nombre, historial, sueldoBruto, fechaContratacion, rol);
		this.contrasena = contrasena;
		this.nivelAcceso = nivelAcceso;
		this.especialidad = especialidad;
		this.vacaciones = vacaciones;
	}

	public void cambiarEspecialidad() {
		// TODO - implement PersonalMedico.cambiarEspecialidad
		throw new UnsupportedOperationException();
	}

	@Override
	public int tomarVacaciones(int n) {
		return 0;
	}

	@Override
	public int inscribir(int nivelAcceso) {
		return 0;
	}

	public String toString() {
		return "PersonalMedico{" + "\n" +
				super.toString() + "\n" +
				"   contrasenia=" + contrasena + "\n" +
				"   nivelAcceso=" + nivelAcceso + "\n" +
				"   especialidad=" + especialidad + "\n" +
				"   vacaciones=" + vacaciones + "\n" +
				'}';
	}

	public int getNivelAcceso() {
		return nivelAcceso;
	}

	@Override
	public boolean contrasenaCorrecta(String contrasena) {
		return this.contrasena.equals(contrasena);
	}
}