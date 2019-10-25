package engine;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class Sprite{
    /** The texture that stores the image for this sprite */
    private Texture[] frameTextures;
 
    /** The width in pixels of this sprite */
    private int         width;
 
    /** The height in pixels of this sprite */
    private int         height;
    /** The actual zoom of the sprite*/
    private float			zoom;
    
    private int frameSpeed = 14;
    private int counter = 0;
    private float rotation = 0;
    
    private int currentFrame = 0;
 
    /**
     * Create a new sprite from a specified image.
     *
     * @param loader the texture loader to use
     * @param ref A reference to the image on which this sprite should be based
     */
    public Sprite(TextureLoader loader, String ref, int nbFrames) {
    try {
    	frameTextures = new Texture[nbFrames];
    	for(int i=0;i<frameTextures.length;i++) {
    		if(nbFrames>1) {
    			frameTextures[i] = loader.getTexture("ressources/" + ref + "/"+ref+i+".png");
    		}else {
    			frameTextures[i] = loader.getTexture("ressources/" + ref +".png");
    		}
		}
    	zoom = 1;
    	width = (int) (frameTextures[0].getImageWidth()*zoom);
    	height = (int) (frameTextures[0].getImageHeight()*zoom);
    } catch (IOException ioe) {
        ioe.printStackTrace();
    }
    }
    
    public void setZoom(float factor) {
    	zoom = factor;
    	width = (int) (frameTextures[0].getImageWidth()*zoom);
    	height = (int) (frameTextures[0].getImageHeight()*zoom);
    }
    
    public void setRotation(float rot){
    	rotation = rot;
    }
    
    
    /**
     * Get the width of this sprite in pixels
     *
     * @return The width of this sprite in pixels
     */
    public int getWidth() {
        return width;
    }
 
    /**
     * Get the height of this sprite in pixels
     *
     * @return The height of this sprite in pixels
     */
    public int getHeight() {
        return height;
    }
 
    /**
     * Draw the sprite at the specified location
     *
     * @param x The x location at which to draw this sprite
     * @param y The y location at which to draw this sprite
     */
    public void draw(int x, int y) {
    	if(frameTextures.length==1) {
    		currentFrame = 0;
    	}else {
    		 if (counter == (frameSpeed - 1)) {
    		      currentFrame = (currentFrame + 1) % frameTextures.length;
    		 }
    		 
    		 counter = (counter + 1) % frameSpeed;
    	}
        
        glEnable(GL_TEXTURE_2D);
        // store the current model matrix
        glPushMatrix();
 
        glColor3f(1, 1, 1);

        // bind to the appropriate texture for this sprite
        frameTextures[currentFrame].bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        // translate to the right location and prepare to draw
        glTranslatef(x, y, 0);
        glRotatef(rotation, 0, 0, 1);
        //glRotate
        // draw a quad textured to match the sprite
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(-width/2, -height/2);
 
            glTexCoord2f(0, frameTextures[currentFrame].getHeight());
            glVertex2f(-width/2, height/2);
 
            glTexCoord2f(frameTextures[currentFrame].getWidth(), frameTextures[currentFrame].getHeight());
            glVertex2f(width/2, height/2);
 
            glTexCoord2f(frameTextures[currentFrame].getWidth(), 0);
            glVertex2f(width/2, -height/2);
        }
        glEnd();
 
        // restore the model view matrix to prevent contamination
        glPopMatrix();
    }
}

