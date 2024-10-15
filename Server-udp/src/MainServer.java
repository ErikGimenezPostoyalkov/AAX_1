public class MainServer {

    public static void main(String[] args) {
        int puerto = 9876;  // Puedes cambiar el puerto
        UDPConcurrentServer server = new UDPConcurrentServer(puerto);
        server.startServer();
    }
}
