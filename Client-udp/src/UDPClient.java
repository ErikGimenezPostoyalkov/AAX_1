import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

// Clase del Cliente UDP
public class UDPClient {

    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;

    public UDPClient(String hostname, int puerto) throws IOException {
        socket = new DatagramSocket();
        serverAddress = InetAddress.getByName(hostname);
        serverPort = puerto;
    }

    // Método para enviar un mensaje al servidor
    public void sendMessage(String mensaje) throws IOException {
        byte[] buf = mensaje.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddress, serverPort);
        socket.send(packet);

        // Recibir la respuesta del servidor
        byte[] responseBuf = new byte[256];
        DatagramPacket responsePacket = new DatagramPacket(responseBuf, responseBuf.length);
        socket.receive(responsePacket);

        // Mostrar la respuesta del servidor
        String respuesta = new String(responsePacket.getData(), 0, responsePacket.getLength());
        System.out.println("Respuesta del servidor: " + respuesta);
    }

    // Cierra la conexión del socket
    public void close() {
        socket.close();
    }
}
