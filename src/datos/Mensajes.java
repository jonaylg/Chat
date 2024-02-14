package datos;

import java.util.ArrayList;

public class Mensajes {
	ArrayList<String> listaMess=new ArrayList<>();

	public Mensajes() {
	}
	
	public synchronized void nuevoMensaje(String s) {
		listaMess.add(s);
	}
	
	public String mostrar() {
		String enviar="";
		for (String s : listaMess) {
			enviar+=s;
		}
		return enviar;
	}
}
