import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClienteUDP{
    public static void main(String[] args) {
        int puerto_servidor = 6666;
        byte[] buffer = new byte[1024];
        int contador = 0;
        Scanner sc = new Scanner(System.in);
        try {
            InetAddress direccionServidor = InetAddress.getByName("10.0.9.117");

            // Se crea el DatagramSocket sin especificar un puerto, lo que hace que el sistema asigne un puerto aleatorio.
            // Esto es suficiente para recibir datagramas, ya que el servidor enviará los datos al puerto en el que el cliente está escuchando.
            DatagramSocket datagramSocket = new DatagramSocket();
            while (true){
                System.out.println("Introduce un mensaje: ");
                String msj = sc.nextLine();
                buffer = msj.getBytes();
                DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length, direccionServidor, puerto_servidor);
                datagramSocket.send(pregunta);

                // Se crea un DatagramPacket con el tamaño de buffer especificado.
                // Este paquete se utilizará para recibir los datos enviados por el cliente.
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

                // Al ejecutar este metodo, el buffer se llena con el mensaje enviado por el cliente.
                // Este metodo BLOQUEA la ejecución del código hasta que se reciba el paquete completo.
                // Si el mensaje enviado es más grande que el tamaño del buffer, el mensaje se truncará.
                datagramSocket.receive(peticion);
                System.out.println("Recibo msj del servidor");
                // Convierte el contenido del buffer recibido en un String para poder procesarlo.
                String msjServidor = new String(peticion.getData());
                System.out.println("msjServidor = " + msjServidor);
                buffer = new byte[1024];
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ClienteUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
