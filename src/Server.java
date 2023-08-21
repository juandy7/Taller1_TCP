import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.Collections;
import java.util.Date;
import java.util.Collection;
import java.util.Enumeration;

public class Server {


    public static void main(String[] args) throws IOException {



        while (true) {
                //1. Hago que el server esté atento a conexiones
                ServerSocket server = new ServerSocket(5001);
                System.out.println("Esperando conexión");
                //3. Conexión aceptada
                Socket socket = server.accept();

                System.out.println("Conexión aceptada: " + socket.getPort());
                System.out.println("Conexión aceptada: " + socket.getInetAddress().getHostName());

                //Request
                InputStream is = socket.getInputStream();
                byte[] buffer = new byte[30000];
                is.read(buffer);
                String request = new String(buffer).trim();
                System.out.println(request);
                String response = "";
                switch (request){
                    case "interfaces":
                        try {
                            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
                            for (NetworkInterface networkInterface : Collections.list(enumeration))
                            {
                                response += displayInfo(networkInterface);
                            }
                        }catch (SocketException se){
                            se.printStackTrace();
                        }
                        break;

                    case "whattimeisit":
                        Date date = new Date();
                        response = date.toString();
                        break;

                    case "remoteipconfig":
                        response =  socket.getInetAddress().getHostAddress();
                        break;
                }

                //6. Server envía
                socket.getOutputStream().write(response.getBytes("UTF-8"));

                //Desconectar
                socket.close();

            }


        }

        public static String displayInfo(NetworkInterface networkInterface){

            String name = networkInterface.getName();
            String displayName = networkInterface.getDisplayName();
            Enumeration<InetAddress> enumeration = networkInterface.getInetAddresses();

            StringBuilder responseBuilder = new StringBuilder("Name: " + name + ", Display name: " + displayName);
            for (InetAddress inetAddress : Collections.list(enumeration))
            {
                String ipAddress = inetAddress.getHostAddress();
                String hostName = inetAddress.getHostName();
                String lel =", IP address: "+ipAddress+", Host name: "+ hostName+"\n";
                responseBuilder.append(lel);

            }

            return responseBuilder.toString();
        }


}