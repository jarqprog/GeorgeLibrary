package enums;

public enum DbTables {

    AUTHORS("Authors"), BOOKS("Books"), PUBLISHERS("Publishers"), TYPE_BOOKS("TypeBooks");

    private String table;

    DbTables(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }
}

