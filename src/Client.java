import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Client {

    public static void main(String[] args) throws IOException {
        //2. Solicitud de conexión
        Socket socket = new Socket("192.168.1.12", 5001);
        System.out.println(socket.getPort());
        System.out.println("Conexión aceptada: "+socket.getInetAddress().getHostName());


        String option = menu();

        switch (option.toLowerCase()){
            case "interfaces":
                socket.getOutputStream().write("interfaces".getBytes("UTF-8"));
                break;

            case "remoteipconfig":
                socket.getOutputStream().write("remoteipconfig".getBytes("UTF-8"));
                break;

            case "whattimeisit":
                socket.getOutputStream().write("whattimeisit".getBytes("UTF-8"));
                break;

            case "rtt":

                if (socket.getInputStream().read() == 1024) {


                }else System.out.println("The message is not 1024 Bytes");

                break;

            case "speed":

                new Thread(()->{
                    while(true){
                        try {
                            byte[] bf = new byte[8192];

                            String rec = new String(bf, "UTF-8");
                            if (socket.getInputStream().read(bf) == 8192){

                            }

                        }catch (IOException ex){ex.printStackTrace();}
                    }
                }).start();
                break;

            default:
                System.out.printf("No existe esa opcion");

        }

        //7. Cliente recibe
        byte[] buffer = new byte[30000];
        socket.getInputStream().read(buffer);
        String received = new String(buffer).trim();
        System.out.println(received);


        Scanner scanner = new Scanner(System.in);
        while (true){
            String msg = scanner.nextLine();
            socket.getOutputStream().write(msg.getBytes("UTF-8"));
        }

    }

    public static String menu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Digite por consola una de las siguientes opciones
                interfaces
                remoteIpconfig
                whatTimeIsIt
                RTT
                speed
                """);
        String opt = scanner.nextLine();
        return opt;
    }
}