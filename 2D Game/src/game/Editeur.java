package game;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.lwjgl.glfw.GLFW;

import engine.Entity;
import engine.Sprite;

public class Editeur extends GameState{
	
	enum Selection{
		SPAWN,POINT,MUR,RIEN
	}
	
	Selection selection;
	int[][] level = new int[(context.height/16)-1][context.width/16];
	ArrayList<Entity> buttons = new ArrayList<Entity>();
	Sprite pacmanSprite;
	Sprite murSprite;
	Sprite pointSprite;
	int mouseX,mouseY;
	boolean mousePressed;
	boolean lastFrameMousePressed;
	
	@Override
	public void initialisation(Game context) {
		super.initialisation(context);
		pacmanSprite = new Sprite(context.textureLoader,"\\pacman\\pacman0",1);
		murSprite = new Sprite(context.textureLoader,"mur",1);
		pointSprite = new Sprite(context.textureLoader,"point",1);
		Entity pacman = new ButtonEntity(pacmanSprite, context.width/16,context.height-10);
		Entity mur = new ButtonEntity(murSprite, (context.width*2)/16,context.height-10);
		Entity points = new ButtonEntity(pointSprite, (context.width*3)/16,context.height-10);
		Entity save = new ButtonEntity(new Sprite(context.textureLoader,"save",1), (context.width*13)/16,context.height-10);
		buttons.add(pacman);
		buttons.add(points);
		buttons.add(mur);
		buttons.add(save);
		selection = Selection.RIEN;
		
		for(int y=0; y<level.length;y++) {
			for(int x=0; x<level[y].length;x++){
				level[y][x] = -1;
			}
		}
	}
	
	@Override
	public void update() {
		mousePressed = context.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) || context.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT);
		double[] mousePos = context.getMousePos();
		mouseX=(int) mousePos[0];
		mouseY=(int) mousePos[1];
		
		
		if(context.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			context.setGameState(new Menu());
		}
		if(mousePressed) {
			Rectangle souris = new Rectangle(mouseX,mouseY,4,4);
System.out.println("Click aux coordonées :" + mouseX + "  " + mouseY);
			if(souris.intersects(new Rectangle(0,0,context.width,((context.height/16)-1) * 16))) {
				int indiceX = mouseX/16;
				int indiceY = mouseY/16;
				
				switch(selection) {
				case MUR:
					level[indiceY][indiceX] = 1;
					break;
				case POINT:
					level[indiceY][indiceX] = 0;
					break;
				case SPAWN:
					level[indiceY][indiceX] = 9;
					break;
				}
				if(context.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
					level[indiceY][indiceX] = -1;
				}
			}else {
				
				for(int i=0;i<buttons.size();i++) {
					if(buttons.get(i).collideWith(souris)){
						switch(i) {
						case 0: selection = Selection.SPAWN;
						System.out.println("Selection d'un spawn");
						break;
						case 1: selection = Selection.POINT;
						System.out.println("Selection d'un point");
						break;
						case 2: selection = Selection.MUR;
						System.out.println("Selection d'un mur");
						break;
						case 3: 
							String rep = JOptionPane.showInputDialog("Numéro du niveau >0");
							try {
							if(Integer.parseInt(rep)<=0) {throw new NumberFormatException();}
								sauvegarder(Integer.parseInt(rep));
							}catch(NumberFormatException e) {
								e.printStackTrace();
								JOptionPane.showConfirmDialog(null, "Entrez un nombre correct!");
							}
						break;
						}
					}
				}
			}
		}
	}

	private void sauvegarder(int nombre) {
		String data = "";
		for(int y=0; y<level.length;y++) {
			for(int x=0; x<level[y].length;x++){
				data += String.valueOf(level[y][x])+",";
				if(x == level[y].length-1) {
					data += "\r\n";
				}
			}
		}	
		System.out.println(data);
		AssetLoader.saveLevel(data, nombre);
	}

	@Override
	public void render() {
		renderLevel();
		drawLines();
		renderEntities();
	}

	private void renderLevel() {
		for(int y=0; y<level.length;y++) {
			for(int x=0; x<level[y].length;x++){
				if(level[y][x]==0) {
					pointSprite.draw(x*pointSprite.getWidth()+8, y*pointSprite.getHeight()+8);
				}else if(level[y][x]==1){
					murSprite.draw(x*murSprite.getWidth()+8,y*murSprite.getHeight()+8);
				} else if(level[y][x]==9){
					pacmanSprite.draw(x*pacmanSprite.getWidth()+8,y*pacmanSprite.getHeight()+8);
				}
			}
		}		
	}

	private void renderEntities() {
		for(Entity ent : buttons) {
			ent.draw();
		}
	}

	private void drawLines() {
        glDisable(GL_TEXTURE_2D);
		
        glPushMatrix();
        glTranslatef(0,0, 0);
        glRotatef(0, 0, 0, 1);

        for(int y = 0; y<context.height/16; y++) {
            glColor3f(1,1,1);
        	glBegin(GL_LINES);
            {
                glVertex2f(0,y*16);
                glVertex2f(context.width, y*16);
            }
            glEnd();
        }
        for(int x = 0; x<context.width/16; x++) {
            glColor3f(1,1,1);
        	glBegin(GL_LINES);
            {
                glVertex2f(x*16,0);
                glVertex2f(x*16, ((context.height/16)-1) * 16);
            }
            glEnd();
        }
        glPopMatrix();
		
	}

	@Override
	public void dispose() {

	}

}
