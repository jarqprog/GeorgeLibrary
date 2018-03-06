package enums;

public enum FilePath {

    DATA_BASE("Library.db");

    private String filePath;

    FilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
