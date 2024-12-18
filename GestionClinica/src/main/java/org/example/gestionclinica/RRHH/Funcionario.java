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
		return  "Rut: " + IDFuncionario + "\n" +
				"Nombre: " + nombre + "\n" +
				"Historial: " + historial + "\n" +
				"SueldoBruto: " + sueldoBruto + "\n" +
				"FechaContratacion: " + fechaContratacion + "\n" +
				"Rol: " + rol + "\n";
	}

	public String getIDFuncionario() {
		return IDFuncionario;
	}

	public String getNombre() {
		return nombre;
	}

	public String getRol() {
		return rol;
	}

	public abstract int getNivelAcceso();

}