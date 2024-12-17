package org.example.gestionclinica.clientes;

import org.example.gestionclinica.RRHH.PersonalMedico;

public class Cita {
	private String fecha;
	private String hora;
	private Paciente paciente;
	private PersonalMedico medico;

	public Cita(String fecha, String hora, Paciente paciente, PersonalMedico medico) {
		this.fecha = fecha;
		this.hora = hora;
		this.paciente = paciente;
		this.medico = medico;
	}

	public void agregarInfoPaciente(int nivelAcceso, Paciente paciente) {
		if(nivelAcceso == 2) {

		}else{
			System.out.println("El nivel de acceso no es el requerido");
		}
	}

	public void agregarEnfermedadCronica(int nivelAcceso) {
		if(nivelAcceso == 2) {
			String info = null;
			//recibe la info
			this.paciente.agregarEnfermedadCronica(info);
		}else{
			System.out.println("El nivel de acceso no es el requerido");
		}
	}

	public void cambiarMedico(PersonalMedico medico, int nivelAcceso) {
		if(nivelAcceso == 2) {
			this.paciente.cambarMedicoTratante(medico);
		}else{
			System.out.println("El nivel de acceso no es el requerido");
		}
	}

	public Paciente getPaciente() {
		return paciente;
	}

	@Override
	public String toString() {
		return  "fecha: " + fecha + "\n" +
				"hora: " + hora + "\n" +
				"paciente: " + paciente.getNombre() + "\n" +
				"medico: " + medico.getNombre() + "\n";
	}
}