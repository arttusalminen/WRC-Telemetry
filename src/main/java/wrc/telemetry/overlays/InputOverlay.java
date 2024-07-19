package wrc.telemetry.overlays;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import generic.data.DataProvider;
import wrc.telemetry.data.types.WrcCustom1Data;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.*;
import wrc.telemetry.data.types.WrcData;
import generic.visuals.Overlay;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class InputOverlay implements Overlay {

    private static final int BAR_HEIGHT = 100;
    private static final int BAR_WIDTH = 20;
    private static final int OVERLAY_WIDTH = 400;
    private static final int OVERLAY_HEIGHT = 200;
    private static final Class<WrcCustom1Data> USED_DATA_CLASS = WrcCustom1Data.class;

    private static final String STEERING_WHEEL_PATH = "resources/steering_wheel.png";

    private float renderThrottle;
    private float renderBrake;
    private float renderHandBrake;
    private float renderSteering;
    private float renderClutch;

    // Visuals
    private int steeringWheelId;
    // private BufferedImage renderSteeringWheelImage;

    private final DataProvider<WrcData> dataProvider;

    long window;

    public InputOverlay(DataProvider<WrcData> dataDataProvider) {
        this.dataProvider = dataDataProvider;
    }

    public void init() {
        if (!glfwInit())
            throw new IllegalStateException("GLFW init failed");

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
        glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, GLFW_TRUE);
        glfwWindowHint(GLFW_FLOATING, GLFW_TRUE);

        window = glfwCreateWindow(OVERLAY_WIDTH, OVERLAY_HEIGHT, "Input overlay", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Window creation failed");

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0))
            );
        }

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        glfwSwapInterval(1);

        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Textures etc
        steeringWheelId = createTexture(STEERING_WHEEL_PATH);

    }

    @Override
    public void deinit() {
        // NOP
    }

    private void updateRenderValues() {
        long curr = System.currentTimeMillis();
        long updatedAtMs = dataProvider.dataLastUpdated();

        WrcCustom1Data prev = dataProvider.getRecentlyAddedData(USED_DATA_CLASS, 1);
        WrcCustom1Data now = dataProvider.getRecentlyAddedData(USED_DATA_CLASS, 0);

        if (now == null || prev == null) {
            renderThrottle = 0;
            renderBrake = 0;
            renderHandBrake = 0;
            renderSteering = 0;
            renderClutch = 0;
        }
        else {
            renderThrottle = prev.getVehicleThrottle() + (now.getVehicleThrottle() - prev.getVehicleThrottle()) * ((curr - updatedAtMs) / (now.getGameDeltaTime() * 1000));
            renderBrake = prev.getVehicleBrake() + (now.getVehicleBrake() - prev.getVehicleBrake()) * ((curr - updatedAtMs) / (now.getGameDeltaTime() * 1000));
            renderHandBrake = prev.getVehicleHandbrake() + (now.getVehicleHandbrake() - prev.getVehicleHandbrake()) * ((curr - updatedAtMs) / (now.getGameDeltaTime() * 1000));
            renderSteering = prev.getVehicleSteering() + (now.getVehicleSteering() - prev.getVehicleSteering()) * ((curr - updatedAtMs) / (now.getGameDeltaTime() * 1000));
            renderClutch = prev.getVehicleClutch() + (now.getVehicleClutch() - prev.getVehicleClutch()) * ((curr - updatedAtMs) / (now.getGameDeltaTime() * 1000));
        }
    }

    public void render() {
        updateRenderValues();

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        glLoadIdentity();

        drawBase();
        drawThrottle();
        drawBrake();
        drawHandBrake();
        drawClutch();
        drawSteering();

        glfwSwapBuffers(window); // swap the color buffers
        glfwPollEvents();
    }

    private void drawBase() {
        glColor4f(0f,0f,0f, 0.5f);
        glBegin(GL_QUADS);
        glVertex2f(-1f, -1f);
        glVertex2f(-1f, 1f);
        glVertex2f(1f, 1f);
        glVertex2f(1f, -1f);
        glEnd();
    }

    private void drawThrottle() {
        glColor4f(0f,0f,0f, 0.8f);
        glBegin(GL_QUADS);
        glVertex2f(0.95f, -0.9f);
        glVertex2f(0.95f, 0.9f);
        glVertex2f(0.8f, 0.9f);
        glVertex2f(0.8f, -0.9f);
        glEnd();

        glColor3f(0f, 1f, 0f);
        glBegin(GL_QUADS);
        glVertex2f(0.95f, -0.9f);
        glVertex2f(0.95f, -0.9f + renderThrottle * 1.8f);
        glVertex2f(0.8f, -0.9f + renderThrottle * 1.8f);
        glVertex2f(0.8f, -0.9f);
        glEnd();
    }

    private void drawBrake() {
        glColor4f(0f,0f,0f, 0.8f);
        glBegin(GL_QUADS);
        glVertex2f(-0.75f, -0.9f);
        glVertex2f(-0.75f, 0.9f);
        glVertex2f(-0.6f, 0.9f);
        glVertex2f(-0.6f, -0.9f);
        glEnd();

        glColor3f(1f, 0f, 0f);
        glBegin(GL_QUADS);
        glVertex2f(-0.75f, -0.9f);
        glVertex2f(-0.75f, -0.9f + renderBrake * 1.8f);
        glVertex2f(-0.6f, -0.9f + renderBrake * 1.8f);
        glVertex2f(-0.6f, -0.9f);
        glEnd();
    }

    private void drawClutch() {
        glColor4f(0f,0f,0f, 0.8f);
        glBegin(GL_QUADS);
        glVertex2f(0.75f, -0.9f);
        glVertex2f(0.75f, 0.9f);
        glVertex2f(0.6f, 0.9f);
        glVertex2f(0.6f, -0.9f);
        glEnd();

        glColor3f(0f, 0f, 1f);
        glBegin(GL_QUADS);
        glVertex2f(0.75f, -0.9f);
        glVertex2f(0.75f, -0.9f + renderClutch * 1.8f);
        glVertex2f(0.6f, -0.9f + renderClutch * 1.8f);
        glVertex2f(0.6f, -0.9f);
        glEnd();
    }

    private void drawHandBrake() {
        glColor4f(0f,0f,0f, 0.8f);
        glBegin(GL_QUADS);
        glVertex2f(-0.95f, -0.9f);
        glVertex2f(-0.95f, 0.9f);
        glVertex2f(-0.8f, 0.9f);
        glVertex2f(-0.8f, -0.9f);
        glEnd();

        glColor3f(1f, 0f, 1f);
        glBegin(GL_QUADS);
        glVertex2f(-0.95f, -0.9f);
        glVertex2f(-0.95f, -0.9f + renderHandBrake * 1.8f);
        glVertex2f(-0.8f, -0.9f + renderHandBrake * 1.8f);
        glVertex2f(-0.8f, -0.9f);
        glEnd();
    }

    private void drawSteering() {
//        renderSimpleSteering();
        glColor4f(1f,1f,1f,1f);
        renderSteeringWheel();
    }


    private void renderSimpleSteering() {
        // Base
        glColor4f(0f,0f,0f, 0.4f);
        glBegin(GL_QUADS);
        glVertex2f(-0.65f, -0.2f);
        glVertex2f(-0.65f, 0.2f);
        glVertex2f(0.65f, 0.2f);
        glVertex2f(0.65f, -0.2f);
        glEnd();

        // Marker
        float steeringCenter = 0.60f * renderSteering;
        glColor3f(1f, 1f, 0f);
        glBegin(GL_QUADS);
        glVertex2f(steeringCenter - 0.05f, -0.2f);
        glVertex2f(steeringCenter - 0.05f, 0.2f);
        glVertex2f(steeringCenter + 0.05f, 0.2f);
        glVertex2f(steeringCenter + 0.05f, -0.2f);
        glEnd();
    }

    private void renderSteeringWheel() {
        glBindTexture(GL_TEXTURE_2D, steeringWheelId);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);

        rotateAndDrawTexture(-renderSteering*100, steeringWheelId, 180);

        glDisable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
    }

    static int createTexture(String filePath) {
        // Load image
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);

        // Use STBImage to load the image
        ByteBuffer image = STBImage.stbi_load(filePath, width, height, comp, 4);
        if (image == null) {
            throw new RuntimeException("Failed to load a texture file!"
                    + System.lineSeparator()
                    + STBImage.stbi_failure_reason());
        }
        int textureID = glGenTextures(); //Generate texture ID
        glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        //Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        //Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        //Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        int w = width.get(0);
        int h = height.get(0);

        glGenerateMipmap(GL_TEXTURE_2D);

        // Remove binding
//        glBindTexture(GL_TEXTURE_2D, 0);

        //Return the texture ID so we can bind it later again
        return textureID;
    }

    private void drawTexture(int textureId,float x, float y,float w, float h) {
        //Main.width and Main.height are the window window width and height.
        float nx = x*2/OVERLAY_WIDTH;
        float ny = y*2/OVERLAY_HEIGHT;
        float nw = w*2/OVERLAY_WIDTH;
        float nh = h*2/OVERLAY_HEIGHT;

        glBegin(GL_QUADS);
        glTexCoord2f(0, 1);
        glVertex2f(nx,ny);
        glTexCoord2f(1, 1);
        glVertex2f(nx+nw,ny);
        glTexCoord2f(1, 0);
        glVertex2f(nx+nw,ny+nh);
        glTexCoord2f(0, 0);
        glVertex2f(nx,ny+nh);
        glEnd();
    }

    void rotateAndDrawTexture(float angle, int textureId, int widthInPixels) {
        glPushMatrix();
//        glTranslatef(
//                x/Main.width*2,
//                y/Main.height*2,
//                0);
        glScalef(1f,(float)OVERLAY_WIDTH/OVERLAY_HEIGHT,1f);
        glRotatef((float)angle,0f,0f,1f);
        glScalef(1f,(float)OVERLAY_HEIGHT/OVERLAY_WIDTH,1f);
//        glTranslatef(
//                -x/Main.width*2,
//                -y/Main.height*2,
//                0);
        drawTexture(textureId,-widthInPixels/2,-widthInPixels/2,widthInPixels,widthInPixels);
        glPopMatrix();
    }


}
