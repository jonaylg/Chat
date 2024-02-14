package hilos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import datos.Mensajes;

public class HiloCliente extends Thread{
	Scanner sc=new Scanner(System.in);
	private Socket cliente;
	DataInputStream flujo_entrada=null;
	DataOutputStream flujo_salida=null;
	String mensaje="";
	Mensajes m=new Mensajes();
	ArrayList<Socket> clientes;
	ArrayList<String> nombres;
	
	public HiloCliente(Socket cliente, Mensajes m, ArrayList<Socket> clientes, ArrayList<String> nombres) {
		this.cliente = cliente;
		this.m=m;
		this.clientes=clientes;
		this.nombres=nombres;
		InputStream inaux=null;
		OutputStream outaux=null;
		try {
			inaux = cliente.getInputStream();
			outaux = cliente.getOutputStream();
			flujo_entrada = new	 DataInputStream(inaux);
			flujo_salida= new DataOutputStream(outaux);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//fin constructor
	
	@Override
	public void run() {
		try {			
			String texto="";
			while (!texto.equalsIgnoreCase("fin")) {
				
				InputStream inaux=cliente.getInputStream();
				DataInputStream flujo_entrada = new	 DataInputStream(inaux);
				texto=flujo_entrada.readUTF();
				Scanner comprobacion=new Scanner(texto);
				if (comprobacion.nextLine().equalsIgnoreCase("nombre:")) {
					OutputStream outaux = cliente.getOutputStream();
					DataOutputStream flujo_salida= new DataOutputStream( outaux );
					String s=comprobacion.nextLine();
					boolean repe=false;
					for (String nom : nombres) {
						if (s.equalsIgnoreCase(nom)) {
							repe=true;
						}
					}
					if (repe) {
						flujo_salida.writeUTF("otro nombre");
					}else {
						nombres.add(s);
						flujo_salida.writeUTF("correcto");
					}
				}else if(texto.equalsIgnoreCase("mensajes_anteriores")){
					flujo_salida.writeUTF(m.mostrar());
				}else {
					synchronized (m) {
						m.nuevoMensaje(texto+"\n");
					}
					System.out.println(texto);
					for (Socket socket : clientes) {
						OutputStream outaux = socket.getOutputStream();
						DataOutputStream flujo_salida= new DataOutputStream( outaux );
						flujo_salida.writeUTF(texto);
					}
				}
				
			}
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("final del fichero");
		}
	}

}
