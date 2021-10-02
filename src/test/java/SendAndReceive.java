import lombok.Getter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.LinkedBlockingDeque;

public class SendAndReceive {

    private final String hostname;
    private final int port;

    {
        hostname = "192.168.1.30";
        port = 50000;
    }

    @Getter
    private final Socket socket;
    @Getter
    private final PrintWriter out;
    private BufferedReader br;
    private LinkedBlockingDeque<String> strings;
    public SendAndReceive() throws IOException, InterruptedException {
        socket = new Socket(hostname, port);
        out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                receive();
            }
        });

        thread.setDaemon(true);
        thread.start();

        out.println("{\"messageId\":\"addNewAnimeEntry\",\"messageBody\":\"{\\\"animeName\\\":\\\"anime\\\",\\\"typeOfQuality\\\":\\\"720p\\\",\\\"serverName\\\":\\\"serverName\\\",\\\"numberOfEpisode\\\":\\\"789\\\"}\"}");
        strings = new LinkedBlockingDeque<String>();
        while (true) {
            System.out.println(strings.take());
        }
    }

    private void receive(){
        String data = null;
        while (true) {
            try {
                if ((data = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            strings.add(data);
        }
    }

    public static void main(String[] args) {
        try {
            new SendAndReceive();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
