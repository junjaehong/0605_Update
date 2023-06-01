package com.DevOOPS.barrier.Status;

import lombok.Data;

@Data
public class Message {

    private StatusEnum status;
    private String message;
    private Object data;
    private int time;

    public Message() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = "에러";
    }

    public Message(StatusEnum status, String message, Object data) {
        this.status = status;
        this.data = data;
        this.message = message;

    }
    public Message(StatusEnum status, String message, Object data, int time) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.time = time;
    }
}
