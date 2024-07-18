package wrc.telemetry;

import wrc.telemetry.data.WrcDataParser;
import wrc.telemetry.overlays.InputOverlay;
import generic.data.DataHandler;
import generic.data.recording.DataRecorder;
import generic.data.parsing.ParsedData;
import wrc.telemetry.data.types.WrcData;
import generic.udp.UdpListener;
import generic.visuals.OverlayRendererImpl;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

public class App {

    private static JFrame frame;

    public static void main(String[] args) throws IOException {
        UdpListener<? extends ParsedData> listener = new UdpListener<>(InetAddress.getByName("localhost"), 20777, List.of(new WrcDataParser()));

        // Data
        DataRecorder<WrcData> recorder = new DataRecorder<>(WrcData.class);
//        StageContainer stageContainer = new StageContainer(recorder);

        // Renderer
        OverlayRendererImpl renderer = new OverlayRendererImpl();

        // Overlays
        InputOverlay inputOverlay = new InputOverlay(recorder);
//        DeltaOverlay deltaOverlay = new DeltaOverlay(recorder, stageContainer);

        // Bind
        renderer.bind(inputOverlay);
//        renderer.bind(deltaOverlay);

        listener.addHandler((DataHandler) recorder);

        // RUN
        listener.run();
    }
}
