package epam.autotests.page_objects.enums;

public enum FiltersGroups {

    ACTIVE_VCF("Active VCF files"),
    GENE("Gene"),
    GENE2("GENE"),
    TYPE_VARIANT("Type of variant"),
    VARIANT_LOCATION("Variant location"),
    VCF("VCF");

    public final String value;

    FiltersGroups(final String value) {
        this.value = value;
    }
}
