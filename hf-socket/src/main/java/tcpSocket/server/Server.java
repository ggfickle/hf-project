package tcpSocket.server;

import lombok.SneakyThrows;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/3 22:35
 */
public class Server {

    @SneakyThrows
    public static void main(String[] args) {
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("server is running...");
        while (true) {
            Socket sock = serverSocket.accept();
            System.out.println("connected from " + sock.getRemoteSocketAddress());
            new Thread(() ->{
                try (InputStream input = sock.getInputStream()) {
                    try (OutputStream output = sock.getOutputStream()) {
                        handle(input, output);
                    }
                } catch (Exception e) {
                    try {
                        sock.close();
                    } catch (IOException ignored) {
                    }
                    System.out.println("client disconnected.");
                }
            }).start();
        }
    }
    private static void handle(InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        writer.write("hello\n");
        writer.flush();
        for (;;) {
            String s = reader.readLine();
            if (s.equals("bye")) {
                writer.write("bye\n");
                writer.flush();
                break;
            }
            writer.write("搜索结果是: " + s + "\n");
            writer.flush();
        }
    }
}
