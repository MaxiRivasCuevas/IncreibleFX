package org.example.gestionclinica.clientes;

import org.example.gestionclinica.RRHH.PersonalMedico;

public class Cita {

	private String IDcita;
	private String hora;
	private String fecha;
	private Paciente paciente;
	private PersonalMedico medico;

	public void agregarInfoPaciente(int nivelAcceso,Paciente paciente) {
		if(nivelAcceso == 2) {

		}else{
			System.out.println("El nivel de acceso no es el requerido");
		}
	}

	public void generarCita(PersonalMedico personalMedico,Paciente paciente){
		String hora = null;
		String fecha=null;
		String IdCita=null;
		this.IDcita=IdCita;
		this.hora= hora;
		this.fecha=fecha;
		this.medico=personalMedico;
		this.paciente=paciente;
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
}