package org.example.gestionclinica.RRHH;

public class PersonalAdmin extends Funcionario implements PersonalInterno {

	private int nivelAcceso;
	private int vacaciones;

	public PersonalAdmin(String IDFuncionario, String nombre, String historial, int sueldoBruto, String fechaContratacion, String rol, int nivelAcceso, int vacaciones) {
		super(IDFuncionario, nombre, historial, sueldoBruto, fechaContratacion, rol);
		this.nivelAcceso = nivelAcceso;
		this.vacaciones = vacaciones;
	}

	public void despedir(Funcionario funcionario) {
		// TODO - implement PersonalAdmin.despedir
		throw new UnsupportedOperationException();
	}

	public void calcularFiniquito(String historial) {
		// TODO - implement PersonalAdmin.calcularFiniquito
		throw new UnsupportedOperationException();
	}

	public void despedir(Funcionario funcionario, String infoEmpresa) {
		// TODO - implement PersonalAdmin.despedir
		throw new UnsupportedOperationException();
	}

	public void cambiarSueldo(Funcionario funcionario) {
		// TODO - implement PersonalAdmin.cambiarSueldo
		throw new UnsupportedOperationException();
	}

	public void contratar() {
		// TODO - implement PersonalAdmin.contratar
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

	@Override
	public String toString() {
		return "PersonalAdmin{" + "\n" +
				super.toString() + "\n" +
				"nivelAcceso=" + nivelAcceso + "\n" +
				"vacaciones=" + vacaciones + "\n" +
				'}';
	}

	public int getNivelAcceso() {
		return nivelAcceso;
	}
}