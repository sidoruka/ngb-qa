package epam.autotests.page_objects.enums;

public enum ExternalDataFiles {

	SMPL1_LUMPY("Sample_1-lumpy"),
	SMPL2_LUMPY("Sample_2-lumpy");
	
	public final String value;
	
	private ExternalDataFiles(final String value){
		this.value = value;
	}
	
}
