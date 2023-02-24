package com.example.demo.domains.aggregate03.entity;


public enum OrderStatus {
    CREATE(1, "创建状态"),
    Paid(2, "已付款待发货状态"),
    SEND(3, "已发货"),
    ARRIVED(4, "已到达"),
    SUCCESS(5, "已签收完成状态"),
    CANCEL(-1, "已取消状态");


    private int code;
    private String message;

    @Override
    public String toString() {
        return this.name() + "(" + this.code + "," + this.message + ")";
    }
    public static String getMessage(int code){
        for(OrderStatus orderStatus : OrderStatus.values()){
            if(orderStatus.getCode() == code){
                return orderStatus.message;
            }
        }
        return null;
    }

    public static OrderStatus getOrderStatus(int code){
        for(OrderStatus orderStatus : OrderStatus.values()){
            if(orderStatus.getCode() == code){
                return orderStatus;
            }
        }
        return null;
    }

    private OrderStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
