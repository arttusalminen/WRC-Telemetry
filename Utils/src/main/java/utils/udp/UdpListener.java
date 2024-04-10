package utils.udp;

import utils.TelemetryData;
import utils.data.DataHandler;
import utils.data.DataParser;
import utils.data.ParsedData;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class UdpListener<D extends ParsedData> {

    private final DatagramSocket socket;
    private final AtomicBoolean run = new AtomicBoolean(false);

    private final List<DataHandler<D>> handlers = new ArrayList<>();
    private final DataParser<D> parser;

    public UdpListener(InetAddress address, int port, DataParser<D> parser) {
        this.parser = parser;
        try {
            SocketAddress socketAddress = new InetSocketAddress(address, port);
            socket = new DatagramSocket(socketAddress);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void addHandler(DataHandler<D> handler) {
        handlers.add(handler);
    }

    public synchronized void removeHandler(DataHandler<D> handler) {
        handlers.remove(handler);
    }

    public void run() throws IOException {
        run.set(true);
        while (run.get()) {
            byte[] buffer = new byte[65535];
            DatagramPacket packet = new DatagramPacket(buffer, 65535);
            socket.receive(packet);
            System.out.println(String.format("%d packet received (ms)", System.currentTimeMillis()));
            forwardReceivedPacket(packet);
        }
    }

    public void stop() {
        run.set(false);
    }

    private synchronized void forwardReceivedPacket(DatagramPacket packet) {
        D data = parser.parse(packet.getData());
        handlers.forEach(h -> h.handleData(data));
    }


}
