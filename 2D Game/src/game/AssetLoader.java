package game;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import engine.Sprite;
import engine.TextureLoader;

public class AssetLoader {

	public static int[][] loadTileMap(String fileData){
		int[][] data = null;
		try {
			String[] lines = fileData.split(":");
			data = new int[lines.length][lines[0].split(",").length];
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

	private static Rectangle[] loadCollision(int[][] data) {
		/*
		fileData = fileData.substring(fileData.indexOf("$")+2);
		String[] lines = fileData.split(":");
		Rectangle[] toReturn = new Rectangle[lines.length];
		for(int i = 0 ; i< lines.length; i++) {
			String[] data = lines[i].split(",");
			toReturn[i] = new Rectangle();
			toReturn[i].setBounds(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]),Integer.parseInt(data[3]));
		}*/
		ArrayList<Rectangle> liste = new ArrayList<Rectangle>();
		for(int y=0; y<data.length; y++) {
			for(int x=0; x<data[y].length; x++) {
				if(data[y][x]==1 || data[y][x]==2 || data[y][x]==3 || data[y][x]==4 || data[y][x]==5 || data[y][x]==6)
					liste.add(new Rectangle(x*16,y*16,16,16));
			}
		}
		Rectangle[] toReturn = new Rectangle[liste.size()];
		for(int i = 0 ; i< toReturn.length; i++) {
			toReturn[i] = liste.get(i);
		}
		
		return toReturn;
	}
	public static Level loadLevel(String fileName,TextureLoader textureLoader) {
		String content = "";
		try {
			//Lire le fichier
			String tamere = new File("ressources\\datas\\"+fileName+".level").getAbsolutePath();
			BufferedReader reader = new BufferedReader(new FileReader(new File("ressources\\levels\\"+fileName+".level")));
			String line = reader.readLine();
			String header;
			while(line!=null) {
				content += line+":";
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		int[][] data = loadTileMap(content);
		
		Sprite mur = new Sprite(textureLoader,"mur", 1);
		
		return new Level(mur,new Sprite(textureLoader,"point", 1),data,loadCollision(data));
	}
	
	public static void saveLevel(String data, int number) {
		new Thread(){
			@Override
			public void run() {
				BufferedWriter writer = null;
				try {
					writer = new BufferedWriter(new FileWriter(new File("ressources\\levels\\"+"stage"+number+".level")));
					writer.write(data);
					writer.flush();
					System.out.println("Enregistrement terminé");
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}.start();
	}
}
