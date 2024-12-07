package a01183994.lab10.database.util;

public enum ErrorCode {
    SUCCESS_ADD(200, "Success"),
    INVALID_DATA(901, "Invalid employee data!"),
    DUPLICATE_ID(502, "ID already exists for another employee"),
    SUCCESS_FIND(000, "Success"),
	NO_MATCH(801, "No match found"),
	SUCCESS_DELETE(001, "Deleted Successfully"),
    DELETE_UNSUCCESSFUL(902, "Delete Unsuccessful"); 

    private final int code;
    private final String description;

    ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() { return code; }
    public String getDescription() { return description; }
}