package majorprojectsem7.core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Uday Kandpal
 */
public class Connection extends Thread {

    private static final int myPort = 50001;
    private static final int AS_SERVER = 0x01;
    private static final int AS_CLIENT = 0x02;

    private int type = AS_CLIENT;
    private static int id = 01;
    private int myId;
    private FileReader fr = null;
    private FileWriter fw = null;
    private String sourcePath = null;

    public Connection() {
        myId = id++;
    }

    public Connection(String source, int type) throws IOException {
        myId = id++;
        this.type = type;
        sourcePath = source;
        if (type == AS_SERVER) {
            fw = new FileWriter(new File(sourcePath));
            fr = null;
        } else {
            fr = new FileReader(new File(sourcePath));
        }
    }

    @Override
    public void run() {
        if (type == AS_SERVER) {
            try {
                boolean received = false;
                while (!received) {
                    System.out.println("Connection #" + myId + ": " + "waiting to receive packets");
                    received = receiveSignal();
                }
                System.out.println("Connection #" + myId + ": " + "received packet successfully. exit!");
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                while (true) {
                    System.out.println("Connection #" + myId + ": " + "sending packet");
                    broadcastDetectorPackets();
                    System.out.println("Connection #" + myId + ": " + "sent packet successfully");
                }
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void broadcastDetectorPackets() throws SocketException, IOException {
        byte buf[] = new byte[256];
        DatagramSocket socket = new DatagramSocket();
        String broadcastAddress = "255.255.255.0";
        InetAddress address = InetAddress.getByName(broadcastAddress);
        // send broadcast request
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            sb.append(fr.read());
        }
        buf = sb.toString().getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, myPort);
        socket.send(packet);
        // wait for response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
    }

    public void unicastDetectorPackets(String broadcastAddress) throws SocketException, IOException {
        byte buf[] = new byte[256];
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(broadcastAddress);
        // send broadcast request
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, myPort);
        socket.send(packet);
        // wait for response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
    }

    public boolean receiveSignal() throws SocketException, IOException {
        byte buffer[] = new byte[256];
        DatagramSocket dst = new DatagramSocket();
        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        dst.receive(dp);
        if (dp.getData() != null) {
            buffer = "RECEIVED SUCCESSSFULLY".getBytes();
            dst.send(new DatagramPacket(buffer, myPort));
            System.out.println(new String(dp.getData()));
            fw.write(new String(dp.getData()));
        }
        return dp.getData() != null;
    }

    public void stopConnection() {
        if (type == AS_SERVER) {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        super.stop();

    }

    public static void main(String[] args) throws Exception {
        Connection con1 = new Connection("in.txt", AS_CLIENT);
        Connection con2 = new Connection("out.txt", AS_SERVER);
        con2.start();
        con1.start();
        con1.stopConnection();
        con2.stopConnection();
    }
}
