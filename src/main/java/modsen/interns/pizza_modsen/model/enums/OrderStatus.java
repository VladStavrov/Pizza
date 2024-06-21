package modsen.interns.pizza_modsen.model.enums;

public enum OrderStatus {
    PENDING,        // Заказ ожидает обработки
    CONFIRMED,      // Заказ подтвержден
    PREPARING,      // Заказ готовится
    READY,          // Заказ готов
    DELIVERING,     // Заказ доставляется
    DELIVERED,      // Заказ доставлен
    CANCELLED       // Заказ отменен
}