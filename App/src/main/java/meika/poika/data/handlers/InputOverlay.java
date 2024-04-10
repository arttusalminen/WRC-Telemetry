package meika.poika.data.handlers;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import utils.data.DataHandler;
import utils.TelemetryData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class InputOverlay implements DataHandler<TelemetryData> {

    private static final int BAR_HEIGHT = 100;
    private static final int BAR_WIDTH = 20;
    private static final int OVERLAY_WIDTH = 400;
    private static final int OVERLAY_HEIGHT = 200;

    private static final String STEERING_WHEEL_PATH = "resources/steering_wheel.png";

    private float deltaTime = 1f;
    private long updatedAtMs = System.currentTimeMillis();

    private float throttle;
    private float brake;
    private float handBrake;
    private float steering;
    private float clutch;


    private float previousThrottle;
    private float previousBrake;
    private float previousHandBrake;
    private float previousSteering;
    private float previousClutch;

    private float renderThrottle;
    private float renderBrake;
    private float renderHandBrake;
    private float renderSteering;
    private float renderClutch;

    // Visuals
    private BufferedImage steeringWheelImage;
    private int steeringWheelId;
    // private BufferedImage renderSteeringWheelImage;

    long window;
    Deque<Long> frameTimes = new ArrayDeque<>();


    public InputOverlay() {

        try {
            steeringWheelImage = ImageIO.read(new File(STEERING_WHEEL_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // LWJGL
        Thread t = new Thread(this::renderLoop);
        t.start();
    }

    private void initLWJGL() {
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
    public synchronized void handleData(TelemetryData data) {
        deltaTime = data.getGameDeltaTime();
        updatedAtMs = System.currentTimeMillis();

        previousBrake = brake;
        previousSteering = steering;
        previousThrottle = throttle;
        previousHandBrake = handBrake;
        previousClutch = clutch;

        throttle = data.getVehicleThrottle();
        brake = data.getVehicleBrake();
        handBrake = data.getVehicleHandbrake();
        steering = data.getVehicleSteering();
        clutch = data.getVehicleClutch();
    }

    private void renderLoop() {
        initLWJGL();

        while (!glfwWindowShouldClose(window)) {
            long curr = System.currentTimeMillis();
            renderThrottle = previousThrottle + (throttle - previousThrottle) * ((curr - updatedAtMs) / (deltaTime * 1000));
            renderBrake = previousBrake + (brake - previousBrake) * ((curr - updatedAtMs) / (deltaTime * 1000));
            renderHandBrake = previousHandBrake + (handBrake - previousHandBrake) * ((curr - updatedAtMs) / (deltaTime * 1000));
            renderSteering = previousSteering + (steering - previousSteering) * ((curr - updatedAtMs) / (deltaTime * 1000));
            renderClutch = previousClutch + (clutch - previousClutch) * ((curr - updatedAtMs) / (deltaTime * 1000));

            render();
        }
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        glLoadIdentity();



        // Base
        glColor4f(0f,0f,0f, 0.5f);
        glBegin(GL_QUADS);
        glVertex2f(-1f, -1f);
        glVertex2f(-1f, 1f);
        glVertex2f(1f, 1f);
        glVertex2f(1f, -1f);
        glEnd();


        // Throttle
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


        // Brake
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


        // Handbrake
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


        // Clutch
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

        // Steering
//        renderSimpleSteering();
        glColor4f(1f,1f,1f,1f);
        renderSteeringWheel();

        glfwSwapBuffers(window); // swap the color buffers
        glfwPollEvents();
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
