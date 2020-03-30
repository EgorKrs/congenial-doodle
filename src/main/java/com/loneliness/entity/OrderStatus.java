package com.loneliness.entity;

public enum  OrderStatus {
    NON_ACTIVE, IN_PROCESSING, EQUIPMENT, PENDING_ISSUANCE, WAITING_FOR_PAYMENT;
    public String getStatus(){
        return name();
    }
}
