package wrc.telemetry.overlays;

import wrc.telemetry.StageContainer;
import generic.data.DataProvider;
import wrc.telemetry.data.types.WrcCustom1Data;
import generic.visuals.Overlay;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Overlay that shows the delta to the best run.
 *
 * Note: This overlay is in early development and is not working as intended yet
 * TODO: Finish this overlay when WRC developers fix the data that is being sent from the game.
 */
public class DeltaOverlay implements Overlay {

    private static final int OVERLAY_WIDTH = 400;
    private static final int OVERLAY_HEIGHT = 200;

    long window;

    private final DataProvider<WrcCustom1Data> dataProvider;
    private final StageContainer stageContainer;

    private JFrame frame;
    private JLabel label;

    private Map<TelemetryKey, List<WrcCustom1Data>> bestRuns = new HashMap<>();


    private List<WrcCustom1Data> bestRun = null;
    private int currentIndex = 0;


    public DeltaOverlay(DataProvider<WrcCustom1Data> dataDataProvider, StageContainer stageContainer) {
        this.dataProvider = dataDataProvider;
        this.stageContainer = stageContainer;
    }

    @Override
    public void init() {
        // Swing
        frame = new JFrame("Delta Overlay");
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setBackground(Color.WHITE);
        frame.setAlwaysOnTop(true);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        frame.setSize(OVERLAY_WIDTH, OVERLAY_HEIGHT);
        frame.setLocation((width - OVERLAY_WIDTH / 2) / 2,
                0);

        // Without this, the window is draggable from any non transparent
        // point, including points  inside textboxes.
        frame.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);

        frame.getContentPane().setLayout(new GridLayout());
        label = new JLabel();
        label.setText("0.0 s");
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);
        label.setSize(OVERLAY_WIDTH, OVERLAY_HEIGHT);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 25));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        frame.getContentPane().add(label);
        frame.setVisible(true);
        frame.pack();
    }

    @Override
    public void render() {
        WrcCustom1Data curr = dataProvider.getRecentlyAddedData(WrcCustom1Data.class, 0);
        WrcCustom1Data prev = dataProvider.getRecentlyAddedData(WrcCustom1Data.class, 1);

        if (curr == null || prev == null) {
            return;
        }

        boolean reloadBestRun = false;
        TelemetryKey currKey = TelemetryKey.forData(curr);
        if (curr.getStageLength() <= curr.getStageCurrentDistance() + 10) {
            // Stage finished
            if (!bestRuns.containsKey(new TelemetryKey(curr))) {
                bestRuns.put(currKey, dataProvider.getAllData(WrcCustom1Data.class));
            }
            else if (bestRuns.get(currKey).getLast().getStageCurrentTime() > curr.getStageCurrentTime()) {
                bestRuns.put(currKey, dataProvider.getAllData(WrcCustom1Data.class));
            }

            dataProvider.clearData();
            reloadBestRun = true;
        }

        if (curr.getStageCurrentTime() < prev.getStageCurrentTime()) {
            // Stage restarted / stage changed
            dataProvider.clearData();
            reloadBestRun = true;
        }

        if (reloadBestRun) {
            bestRun = bestRuns.get(currKey);
            currentIndex = 0;
        }

        if (bestRun == null)
            frame.setVisible(false);
        else if (!frame.isVisible()) {
            frame.setVisible(true);

            float delta = 0f;
            for (int i = currentIndex; i < bestRun.size(); i++) {
                double currDist = curr.getStageCurrentDistance();
                if (bestRun.get(i).getStageCurrentDistance() > currDist) {
                    delta = curr.getStageCurrentTime() - bestRun.get(i).getStageCurrentTime();
                    currentIndex = i;
                    break;
                }
            }
            label.setText(String.format("%.2f s", delta));
        }

        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void deinit() {

    }


    public static class TelemetryKey {
        private final int country;
        private final int stage;
        private final int carClass;

        TelemetryKey(WrcCustom1Data data) {
            this.country = data.getLocationId();
            this.stage = data.getRouteId();
            this.carClass = data.getVehicleClassId();
        }

        public static TelemetryKey forData(WrcCustom1Data telemetryData) {
            return new TelemetryKey(telemetryData);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TelemetryKey that = (TelemetryKey) o;
            return country == that.country && stage == that.stage && carClass == that.carClass;
        }

        @Override
        public int hashCode() {
            return Objects.hash(country, stage, carClass);
        }
    }
}
