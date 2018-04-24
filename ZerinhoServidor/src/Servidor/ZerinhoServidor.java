package Servidor;

import java.awt.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ZerinhoServidor {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try {
			ServerSocket server = new ServerSocket(12345);
			System.out.println("Servidor escutando na porta 12345");
			ArrayList<Socket> clients = new ArrayList<Socket>();
			ArrayList<ObjectOutputStream> saidas = new ArrayList<ObjectOutputStream>();
			ArrayList<ObjectInputStream> entradas = new ArrayList<ObjectInputStream>();
			while(true) {
				if(clients.size() <= 3) {
					Socket client  = server.accept();
					clients.add(client);
					System.out.println("Cliente conectado: " + client.getInetAddress().getHostAddress());
					
					ObjectOutputStream saida = new ObjectOutputStream(client.getOutputStream());
					saida.flush();
			        saida.writeObject("Conectou!!");
			        
			       try {
			       ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
					String texto = (String) entrada.readObject();
					System.out.println(texto);
					
			       }catch (SocketException e) {
			    	   System.out.println(e);
			       }
			    
			    }else {
			    	break;
			    }
			}
			
			System.out.println("Que comecem os jogos!!");
			int x = clients.size()-1;
			
			while(clients.size() > 2) {
				saidas = new ArrayList<ObjectOutputStream>();
				entradas = new ArrayList<ObjectInputStream>();
				int[] rodada = new int[3];
				for(int i = 0; i<3; i++) {
					saidas.add(new ObjectOutputStream(clients.get(i).getOutputStream()));
					saidas.get(i).flush();
			        saidas.get(i).writeObject("Sua vez de jogar! Mande um 0 ou 1");
			        try {
					   entradas.add(new ObjectInputStream(clients.get(i).getInputStream()));
					   String texto = (String) entradas.get(i).readObject();
					   rodada[i] = Integer.parseInt(texto);	
					   System.out.println("Cliente " + clients.get(i).getInetAddress().getHostAddress() + "mandou: "+ texto);
							
					 }catch (SocketException e) {
					    System.out.println(e);
					 }
			        
				}
				if(rodada[0]==rodada[1] && rodada[0]==rodada[2] && rodada[2]==rodada[1]) {
					saidas.get(0).writeObject("Empate!!");
					saidas.get(1).writeObject("Empate!!");
					saidas.get(2).writeObject("Empate!!");
					continue;
				}
				if(rodada[0]!=rodada[1] && rodada[0]!=rodada[2] && rodada[2]==rodada[1]) {
					saidas.get(0).writeObject("O cliente " + clients.get(0).getInetAddress().getHostAddress() + "ganhou!");
					saidas.get(1).writeObject("O cliente " + clients.get(0).getInetAddress().getHostAddress() + "ganhou!");
					saidas.get(2).writeObject("O cliente " + clients.get(0).getInetAddress().getHostAddress() + "ganhou!");
					Socket venc = clients.get(0);
					clients.remove(venc);
				}
				if(rodada[0]!=rodada[1] && rodada[0]==rodada[2] && rodada[2]!=rodada[1]) {
					saidas.get(0).writeObject("O cliente " + clients.get(1).getInetAddress().getHostAddress() + "ganhou!");
					saidas.get(1).writeObject("O cliente " + clients.get(1).getInetAddress().getHostAddress() + "ganhou!");
					saidas.get(2).writeObject("O cliente " + clients.get(1).getInetAddress().getHostAddress() + "ganhou!");
					Socket venc = clients.get(1);
					clients.remove(venc);
				}
				if(rodada[0]==rodada[1] && rodada[0]!=rodada[2] && rodada[2]!=rodada[1]) {
					saidas.get(0).writeObject("O cliente " + clients.get(2).getInetAddress().getHostAddress() + "ganhou!");
					saidas.get(1).writeObject("O cliente " + clients.get(2).getInetAddress().getHostAddress() + "ganhou!");
					saidas.get(2).writeObject("O cliente " + clients.get(2).getInetAddress().getHostAddress() + "ganhou!");
					Socket venc = clients.get(2);
					clients.remove(venc);
				}
				
				x--;
			}
		
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}


	}

}