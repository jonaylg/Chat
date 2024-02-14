package utilServer;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import datos.Mensajes;
import hilos.HiloCliente;

public class MainServerFichero {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int puerto=6001;
		ServerSocket servidor=null;
		Mensajes m=new Mensajes();
		ArrayList<Socket>clientes=new ArrayList<>();
		ArrayList<String>nombres=new ArrayList<>();
		try {
			
			servidor=new ServerSocket(puerto);
			System.out.println("escucho en el puerto "+puerto);
			while (true) {
				Socket cliente1=servidor.accept();
				clientes.add(cliente1);
				Thread hilo=new Thread(new HiloCliente(cliente1, m, clientes,nombres));
				hilo.start();
			}
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
