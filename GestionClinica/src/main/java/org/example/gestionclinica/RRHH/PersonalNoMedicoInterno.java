package org.example.gestionclinica.RRHH;

import org.example.gestionclinica.clientes.Paciente;

public class PersonalNoMedicoInterno extends Funcionario implements PersonalInterno {

	private int nivelAcceso;
	private int vacaciones;

	public PersonalNoMedicoInterno(String IDFuncionario, String nombre, String historial, int sueldoBruto, String fechaContratacion, String rol, int nivelAcceso, int vacaciones) {
		super(IDFuncionario, nombre, historial, sueldoBruto, fechaContratacion, rol);
		this.nivelAcceso = nivelAcceso;
		this.vacaciones = vacaciones;
	}

	public void inscribirPaciente(int nivelAcceso) {
		// TODO - implement PersonalNoMedicoInterno.inscribirPaciente
		throw new UnsupportedOperationException();
	}

	public void inscribirCita(int nivelAcceso, Paciente paciente) {

	}

	@Override
	public int tomarVacaciones(int n) {
		return 0;
	}

	@Override
	public int inscribir(int nivelAcceso) {
		return 0;
	}

	public int getNivelAcceso() {
		return nivelAcceso;
	}
}