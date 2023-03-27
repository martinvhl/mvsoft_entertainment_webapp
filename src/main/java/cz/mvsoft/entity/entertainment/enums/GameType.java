package cz.mvsoft.entity.entertainment.enums;

//for future searchbygametype functionality
public enum GameType {
	
	FIRST_PERSON_SHOOTER("fps"), THIRD_PERSON_SHOOTER("tps"), REAL_TIME_STRATEGY("rts"), TURN_BASED_STRATEGY("turnbased"), 
	ROLE_PLAYING_GAME("rpg"), ADVENTURE("adventure"), ACTION_ADVENTURE("action_adventure"), SPORTS("sport"), RACING("racing"),
	SIMULATOR("simulator"), MOBA("moba"), BATTLE_ROYAL("battle_royal");
	
	private final String displayName;
	
	GameType(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
