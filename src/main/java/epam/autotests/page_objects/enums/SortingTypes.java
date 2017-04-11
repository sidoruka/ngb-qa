package epam.autotests.page_objects.enums;

public enum SortingTypes {

    ASC("Sort Ascending"),
    DESC("Sort Descending");

    public final String value;

    SortingTypes(final String value) {
        this.value = value;
    }
}
