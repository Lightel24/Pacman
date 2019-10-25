package engine;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.DoubleBuffer;
public class Display {
	
	private long window;
	private DoubleBuffer mouseX;
	private DoubleBuffer mouseY;
	
	
	public Display(int width, int height, String nom) {
		
		mouseX = BufferUtils.createDoubleBuffer(1);
		mouseY = BufferUtils.createDoubleBuffer(1);
		//Initialisation
		if(!GLFW.glfwInit()) {
			System.err.println("C'est la merde, GLFW marche pas!");
		}
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // non redimensionable
		
		//Creation
		window = glfwCreateWindow(width, height, nom, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Pas pu créer la fenetre :(");

		//Contexte
		glfwMakeContextCurrent(window);
		// Desactiver la v-sync
		glfwSwapInterval(0);

		//Rendre la fenetre visible
		glfwShowWindow(window);
		GL.createCapabilities();
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glfwSetInputMode(window,GLFW_CURSOR,GLFW_CURSOR_NORMAL);

        //On fait de la 2D, pas besoins de profondeur
        glDisable(GL_DEPTH_TEST);
	}
	
	public void clear() {
		 glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
         glMatrixMode(GL_MODELVIEW);
         glLoadIdentity();
	}
	
	public void close() {
		glfwSetWindowShouldClose(window, true);
	}
	
	public boolean isClosing() {
		return glfwWindowShouldClose(window);
	}
	
	public void update() {
		glfwPollEvents();
		glfwSwapBuffers(window);
	}
	
	public long getWindow() {
		return window;
	}
	
	public boolean isKeyDown(int keycode) {
		return GLFW.glfwGetKey(window, keycode) == GLFW_TRUE;
	}
	
	public boolean isMouseButtonDown(int buttonCode) {
		return glfwGetMouseButton(window, buttonCode) == GLFW_TRUE;
	}
	
	public double[] getMousePos() {
		glfwGetCursorPos(window, mouseX, mouseY);
		double x = mouseX.get();
		mouseX.clear();
		double y = mouseY.get();
		mouseY.clear();
		return new double[] {x,y};
	}

}
