import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread
{

    public Server()
    {
    }

    public static void main(String args[])
        throws InterruptedException
    {
        ServerSocket serversocket = null;
        System.err.println("Test Server - Started");
        try
        {
            serversocket = new ServerSocket(8023);
        }
        catch(IOException ioexception)
        {
            System.out.println("Test Server - Could not listen on port: 8023");
            System.exit(-1);
        }
        System.err.println("Test Server - Running");
        Socket socket = null;
        try
        {
            socket = serversocket.accept();
            System.err.println("Test Server - Client Connection Accepted");
        }
        catch(Exception exception)
        {
            System.err.println("Test Server - Accept failed.");
            System.exit(1);
        }
        do
        {
            boolean flag = false;
            File file = new File("scan.txt");
            if(file.exists())
                try
                {
                    FileReader filereader = new FileReader(file);
                    BufferedReader bufferedreader = new BufferedReader(filereader);
                    StringBuffer stringbuffer = new StringBuffer();
                    String s = bufferedreader.readLine();
                    System.out.println((new StringBuilder()).append("Test Server - HERE IS THE STRING - ").append(s).toString());
                    do
                    {
                        if(s == null)
                            break;
                        String s1 = "";
                        System.err.println(s);
                        s1 = s.substring(6);
                        s1 = (new StringBuilder()).append("Reply:Timestamp 31ff, SpecificID 804c1b61, ReadAddress 000a, Data: 1c7c 00").append(Integer.toHexString(Integer.valueOf(s1).intValue

()).substring(0, 2)).append(' ').append(Integer.toHexString(Integer.valueOf(s1).intValue()).substring(2)).toString();
                        stringbuffer.append(s1);
                        PrintWriter printwriter = new PrintWriter(socket.getOutputStream(), true);
                        printwriter.print((new StringBuilder()).append(stringbuffer.toString()).append("\r\n").toString());
                        System.out.println((new StringBuilder()).append("Test Server - ").append(stringbuffer.toString()).toString());
                        s = "";
                        printwriter.flush();
                        s = bufferedreader.readLine();
                        if(s == null)
                            break;
                        stringbuffer.setLength(0);
                    } while(true);
                    System.out.println(stringbuffer.toString());
                    filereader.close();
                }
                catch(Exception exception1)
                {
                    exception1.printStackTrace();
                    System.err.println("Test Server - File Not Found Exception");
                }
            file.renameTo(new File("oldscan.txt"));
            file.delete();
            sleep(10000L);
        } while(true);
    }

    private static final String logHeader = "Test Server - ";
}
