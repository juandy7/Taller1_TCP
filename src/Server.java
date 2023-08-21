import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(6000);

        while (true) {
            System.out.println("Esperando solicitud");
            Socket socket = server.accept();
            System.out.println("Cliente conectado");

            //Request
            InputStream is = socket.getInputStream();
            byte[] buffer = new byte[1024];
            is.read(buffer);
            String request = new String(buffer).trim();
            System.out.println(request);

            //Procesar el request
            String response = "";
            if (request.equals("whattimeisit")) {
                Date date = new Date();
                response = date.toString();
            } else if (request.startsWith("SUM")) {
                request = request.replace("SUM", "");
                request = request.replace("(", "");
                request = request.replace(")", "");
                String[] ops = request.split(",");
                int A = Integer.parseInt(ops[0]);
                int B = Integer.parseInt(ops[1]);
                response = (A + B) + "";

                //1. Hago que el server esté atento a conexiones
                ServerSocket server2 = new ServerSocket(5001);
                System.out.println("Esperando conexión");
                //3. Conexión aceptada
                Socket socket1 = server2.accept();

                System.out.println("Conexión aceptada: " + socket1.getPort());
                System.out.println("Conexión aceptada: " + socket1.getInetAddress().getHostName());

                //5. Server recibe mensaje
                byte[] bufferr = new byte[300];
                socket1.getInputStream().read(bufferr);
                String received = new String(bufferr, "UTF-8");
                System.out.println(received.trim());

                //6. Server envía
                String mensaje = "Hola desde el server";
                socket1.getOutputStream().write(mensaje.getBytes("UTF-8"));


                //new Thread(()->{
                // ... Codigo en segundo plano
                // }).start();
                new Thread(() -> {
                    while (true) {
                        try {
                            byte[] bf = new byte[300];
                            socket.getInputStream().read(bf);
                            String rec = new String(bf, "UTF-8");
                            System.out.println(rec.trim());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();

                //Enviar el response
                OutputStream os = socket1.getOutputStream();
                os.write(response.getBytes());

                //Desconectar
                socket1.close();

                //192.168.130.37
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String msg = scanner.nextLine();
                    socket1.getOutputStream().write(msg.getBytes("UTF-8"));
                }


            }


        }
    }
}