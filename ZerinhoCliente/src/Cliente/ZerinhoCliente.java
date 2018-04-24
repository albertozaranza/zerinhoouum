package Cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ZerinhoCliente {

    public static void main(String[] args) throws IOException {

        try{
            Socket cliente = new Socket("127.0.0.1", 12345);
            System.out.println("O cliente se conectou ao servidor!");

            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            String textao = (String) entrada.readObject();
            System.out.println(textao);

            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
            saida.flush();
            saida.writeObject("1");
            
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            String s = (String) ois.readObject();
            System.out.println(s);

            ObjectOutputStream x = new ObjectOutputStream(cliente.getOutputStream());
            x.flush();
            x.writeObject("1");
            
            ObjectInputStream y = new ObjectInputStream(cliente.getInputStream());
            String ss = (String) y.readObject();
            System.out.println(ss);

            entrada.close();
            saida.close();
            cliente.close();
        } catch (Exception e){
            System.out.println("O cliente não se conectou ao servidor!");
        }

    }

}
