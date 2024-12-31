package org.example.gestionclinica.RRHH;

public class PersonalNoMedicoExterno extends Funcionario {

	private String empresa;
	private String infoContactoEmpresa;

	public PersonalNoMedicoExterno(String IDFuncionario, String nombre, String historial, int sueldoBruto, String fechaContratacion, String rol, String empresa, String infoContactoEmpresa) {
		super(IDFuncionario, nombre, historial, sueldoBruto, fechaContratacion, rol);
		this.empresa = empresa;
		this.infoContactoEmpresa = infoContactoEmpresa;
	}

	public int getNivelAcceso() {
		return 10;
	}
}