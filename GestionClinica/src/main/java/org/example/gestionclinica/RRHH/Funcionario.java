package org.example.gestionclinica.RRHH;

public abstract class Funcionario {

	private String IDFuncionario;
	private String nombre;
	private String historial;
	private int sueldoBruto;
	private String fechaContratacion;
	private String rol;


	public Funcionario(String IDFuncionario, String nombre, String historial, int sueldoBruto, String fechaContratacion, String rol) {
		this.IDFuncionario = IDFuncionario;
		this.nombre = nombre;
		this.historial = historial;
		this.sueldoBruto = sueldoBruto;
		this.fechaContratacion = fechaContratacion;
		this.rol = rol;
	}

	public void felicitar() {
		String motivo = null;
		this.historial += motivo;
	}

	public String toString() {
		return  " IDFuncionario='" + IDFuncionario + "\n" +
				"   nombre=" + nombre + "\n" +
				"   historial=" + historial + "\n" +
				"   sueldoBruto=" + sueldoBruto + "\n" +
				"   fechaContratacion=" + fechaContratacion + "\n" +
				"   rol=" + rol;
	}

	public String getIDFuncionario() {
		return IDFuncionario;
	}

	public String getNombre() {
		return nombre;
	}

	public abstract int getNivelAcceso();
}