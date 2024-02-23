package utilCliente;


import datos.Cuadro;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ClienteFichero extends Thread{
static Scanner sc=new Scanner(System.in);
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		try {
			Socket sCliente = new Socket("127.0.0.1", 6001);
			System.out.println("conectado");
			boolean repetido=true;
			String nick;
			do {
				System.out.println("dime tu nickname:");
				nick=sc.nextLine();		
				
				OutputStream out = sCliente.getOutputStream();
	            DataOutputStream flujo_salida = new DataOutputStream(out);
	            flujo_salida.writeUTF("nombre:\n"+nick);
	            
	            InputStream in = sCliente.getInputStream();
	            DataInputStream flujo_entrada = new DataInputStream(in);
	            if (flujo_entrada.readUTF().equalsIgnoreCase("correcto")) {
					repetido=false;
				}
			} while (repetido);

			Cuadro c=new Cuadro(nick);
			c.setVisible(true);

			System.out.println("quieres ver los mensajes anteriores?");
			if (sc.nextLine().equalsIgnoreCase("si")) {
				OutputStream out = sCliente.getOutputStream();
	            DataOutputStream flujo_salida = new DataOutputStream(out);
	            flujo_salida.writeUTF("mensajes_anteriores");
	            InputStream in = sCliente.getInputStream();
	            DataInputStream flujo_entrada = new DataInputStream(in);
				String mensaje=flujo_entrada.readUTF();
	            System.out.println(mensaje);
				c.recibirMensaje(mensaje);
			}

			System.out.println("Ya puedes escribir");
			final String nickname=nick; 
			
			Thread recibirMensaje = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream in = sCliente.getInputStream();
                        DataInputStream flujo_entrada = new DataInputStream(in);
                        String mensaje;
                        while ((mensaje = flujo_entrada.readUTF()) != null) {
                            System.out.println(mensaje);
							c.recibirMensaje(mensaje);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
			
			recibirMensaje.start();
			
			Thread enviarMensaje = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OutputStream out = sCliente.getOutputStream();
                        DataOutputStream flujo_salida = new DataOutputStream(out);
                        String texto;
                        do {
                            texto = c.esperarEnter();
							//texto = sc.nextLine();
                            if (!texto.equalsIgnoreCase("")&&texto!=null){
								flujo_salida.writeUTF(nickname+": "+texto);
							}
                        } while (!texto.equalsIgnoreCase("fin"));
                        flujo_salida.close();
                        sCliente.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
			
			enviarMensaje.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
