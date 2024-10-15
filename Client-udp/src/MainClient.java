import java.io.IOException;
import java.util.Scanner;

public class MainClient {

    public static void main(String[] args) {
        String hostname = "localhost";  // Dirección del servidor
        int puerto = 9876;  // Puerto del servidor

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Introduce tu número de cliente:");
            String clienteNumero = scanner.nextLine();

            // Crear el cliente
            UDPClient client = new UDPClient(hostname, puerto);

            String mensaje;
            while (true) {
                System.out.println("Introduce el mensaje para enviar (escribe 'salir' para cerrar la conexión):");
                mensaje = scanner.nextLine();

                if (mensaje.equalsIgnoreCase("salir")) {
                    System.out.println("Cerrando la conexión...");
                    break;
                }

                // Enviar el mensaje al servidor con el número de cliente
                client.sendMessage("Cliente " + clienteNumero + ": " + mensaje);
            }

            // Cerrar el socket del cliente
            client.close();

        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
