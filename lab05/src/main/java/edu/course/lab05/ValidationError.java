package edu.course.lab05;

public final class ValidationError {
    private final int lineNumber;
    private final String field;
    private final String message;

    public ValidationError(int lineNumber, String field, String message) {
        if (lineNumber < 1) {
            throw new IllegalArgumentException("Line number must be positive");
        }
        if (field == null || field.isBlank()) {
            throw new IllegalArgumentException("Field cannot be empty");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        this.lineNumber = lineNumber;
        this.field = field;
        this.message = message;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Line " + lineNumber + ", " + field + ": " + message;
    }
}
