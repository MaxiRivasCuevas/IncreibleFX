package org.example.gestionclinica.clientes;

import org.example.gestionclinica.RRHH.PersonalMedico;
import java.util.ArrayList;

public class Paciente {
	private String RUT;
	private String nombre;
	private String contrasena;
	private String historial;
	private int edad;
	private String enfermedadCronica;
	private PersonalMedico medicoTratante;
	private ArrayList<Cita> citas;

	public Paciente(String RUT, String nombre, String contrasena, String historial, int edad, String enfermedadCronica) {
		this.RUT = RUT;
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.historial = historial;
		this.edad = edad;
		this.enfermedadCronica = enfermedadCronica;
		this.citas=new ArrayList<>();
	}

	public void setMedicoTratante(PersonalMedico medicoTratante) {
		this.medicoTratante = medicoTratante;
	}

	public void agregarCita(int nivelAcceso) {
		if(nivelAcceso<=2){
			Cita citaTemporal = new Cita();
			String RUT=null;
			String nombre=null;
			String contrasena=null;
			String historial=null;
			int edad = 0;
			String enfermedadCronica=null;
			Paciente paciente = new Paciente(RUT,nombre,contrasena,historial,edad,enfermedadCronica);
			citaTemporal.generarCita(medicoTratante,paciente);
			citas.add(citaTemporal);
		}else {
			System.out.println("El nivel de acceso no es el requerido");
		}
	}

	public void quitarCita(int nivelAcceso,int numCita) {
		if(nivelAcceso<=2){
			citas.remove((numCita-1));
			System.out.println("Se removio la cita");
		}else {
			System.out.println("El nivel de acceso no es el requerido");
		}
	}

	public void verCitas(int nivelAcceso) {
		if(nivelAcceso<=2){
			for (Cita cita:citas){
				System.out.println(cita);
			}
		}else {
			System.out.println("El nivel de acceso no es el requerido");
		}
	}

	public void cambarMedicoTratante(PersonalMedico medico) {
		this.medicoTratante=medico;
		System.out.println("Se ha tranferido de medico");
		System.out.println("Nuevo medico: "+medico.getNombre());
	}

	public void agregarInfo(String info){
		this.historial += info + "\n";
	}

	public void agregarEnfermedadCronica(String info){
		this.enfermedadCronica += info + "\n";
	}

	@Override
	public String toString() {
		return "Paciente{" +
				"RUT='" + RUT + '\'' +
				", nombre='" + nombre + '\'' +
				", contrasena='" + contrasena + '\'' +
				", historial='" + historial + '\'' +
				", edad='" + edad + '\'' +
				", enfermedadCronica='" + enfermedadCronica + '\'' +
				", medicoTratante=" + medicoTratante +
				'}';
	}
}