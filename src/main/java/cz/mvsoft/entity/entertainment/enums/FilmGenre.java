package cz.mvsoft.entity.entertainment.enums;

//for future findbygenre functionality
public enum FilmGenre {
	
	DRAMA("Drama"), SCIFI("Sci-fi"), COMICS("Comics"), WESTERN("Western"), HORROR("Horror"), HISTORIC("History"), WAR("War"), DOCUMENT("Document"),
	ROMANTIC("Romantic"), ADVENTURE("Adventure"), CARTOON("Cartoon"), FAIRY_TALE("Fairy-tale"), FANTASY("Fantasy"), COMEDY("Comedy");

	private final String displayName;
	
	FilmGenre(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
