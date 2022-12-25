package ru.ssau.delivery.models;

public enum EOrderStatus {
    WAITING_CONFIRMATION,
    WAITING_COURIER,
    IN_PROGRESS,
    DONE,
    CANCELED,
    WARNING
}
