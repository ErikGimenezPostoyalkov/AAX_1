import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPConcurrentServer {

    private int puerto;

    public UDPConcurrentServer(int puerto) {
        this.puerto = puerto;
    }

    public void startServer() {
        try (DatagramSocket socket = new DatagramSocket(puerto)) {
            System.out.println("Servidor UDP corriendo en el puerto " + puerto);

            while (true) {
                // Preparamos el buffer para recibir los datos
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                
                // Recibir el paquete (bloqueante)
                socket.receive(packet);

                // Lanza un nuevo hilo para manejar este paquete
                new Thread(new ClientHandler(socket, packet)).start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    // Clase que maneja a cada cliente en un nuevo hilo
    private static class ClientHandler implements Runnable {
        private DatagramSocket socket;
        private DatagramPacket packet;

        public ClientHandler(DatagramSocket socket, DatagramPacket packet) {
            this.socket = socket;
            this.packet = packet;
        }

        @Override
        public void run() {
            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();
            String message = new String(packet.getData(), 0, packet.getLength());

            // Imprime el mensaje recibido
            System.out.println("Mensaje recibido de " + clientAddress + ":" + clientPort + " - " + message);

            // Opcionalmente, puedes responder al cliente
            String response = "Mensaje recibido: " + message;
            byte[] responseBytes = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, clientAddress, clientPort);

            try {
                socket.send(responsePacket);
            } catch (IOException e) {
                System.err.println("Error al enviar respuesta: " + e.getMessage());
            }
        }
    }
}
