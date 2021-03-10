package by.fpmi.rest.task.sockets;

import by.fpmi.rest.task.api.NonExistedContactException;
import by.fpmi.rest.task.sockets.data.ClientRequest;
import by.fpmi.rest.task.sockets.parsers.RequestReader;
import by.fpmi.rest.task.sockets.parsers.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class SocketProcessor implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(SocketProcessor.class);

    private final Socket socket;
    private final RequestHandler handler;
    private final RequestReader reader;

    public SocketProcessor(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new RequestReader(socket.getInputStream());
        this.handler = new RequestHandler(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            ClientRequest request = reader.read();
            handler.handle(request);
        } catch (IOException | NonExistedContactException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }

        }
    }
}
