package epam.autotests.page_objects.enums;

public enum SortingTypes {

    ASC("Sort Ascending"),
    DESC("Sort Descending"),
    NONE("None Sorting");

    public final String value;

    SortingTypes(String value) {
        this.value = value;
    }
}
