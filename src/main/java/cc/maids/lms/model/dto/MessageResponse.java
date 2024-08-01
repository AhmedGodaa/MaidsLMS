package cc.maids.lms.model.dto;

public class MessageResponse<T> {
    private String message;
    private T data;

    public MessageResponse() {
    }

    public MessageResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}