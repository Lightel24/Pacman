package game;

public abstract class GameState {
	protected Game context;
	
	public void initialisation(Game context) {
		this.context = context;
	}
	public abstract void update();
	public abstract void render();
	public abstract void dispose();
	
	protected void changeGameState(GameState state) {
		context.setGameState(state);
	}
}
