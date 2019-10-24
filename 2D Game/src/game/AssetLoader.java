package game;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import engine.Sprite;
import engine.TextureLoader;

public class AssetLoader {

	public static int[][] loadTileMap(String fileData){
		int[][] data = null;
		try {
			fileData = fileData.substring(0,fileData.indexOf("$"));
			String[] lines = fileData.split(":");
			data = new int[lines.length][lines[0].length()/2];
			for(int y=0; y<lines.length; y++) {
				String[] cases = lines[y].split(",");
				for(int x=0; x<cases.length; x++) {
					data[y][x] = Integer.parseInt(cases[x]);
				}
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return  new int[][]
			{	 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
			};
		}
		return data;
	}

	private static Rectangle[] loadCollision(String fileData) {
		
		fileData = fileData.substring(fileData.indexOf("$")+2);
		String[] lines = fileData.split(":");
		Rectangle[] toReturn = new Rectangle[lines.length];
		for(int i = 0 ; i< lines.length; i++) {
			String[] data = lines[i].split(",");
			toReturn[i] = new Rectangle();
			toReturn[i].setBounds(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]),Integer.parseInt(data[3]));
		}
		return toReturn;
	}
	public static Level loadLevel(String fileName) {
		String content = "";
		try {
			//Lire le fichier
			String tamere = new File("ressources\\levels\\"+fileName+".level").getAbsolutePath();
			BufferedReader reader = new BufferedReader(new FileReader(new File("ressources\\levels\\"+fileName+".level")));
			String line = reader.readLine();
			String header;
			while(line!=null) {
				if(line.startsWith("header:")) {
					header = line;
				}else {
					content += line+":";
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Level(new Sprite(new TextureLoader(),"mur.png"),loadTileMap(content),loadCollision(content));
	}
}
