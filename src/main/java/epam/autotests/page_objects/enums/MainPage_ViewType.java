package epam.autotests.page_objects.enums;

public enum MainPage_ViewType {

	TABLE("table"),
	TILES("tiles");
	
	public final String value;
	
	private MainPage_ViewType(final String value){
		this.value = value;
	}
}
