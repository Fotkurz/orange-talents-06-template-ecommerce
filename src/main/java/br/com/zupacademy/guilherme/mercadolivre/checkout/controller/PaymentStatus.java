package br.com.zupacademy.guilherme.mercadolivre.checkout.controller;

import java.util.HashMap;
import java.util.Map;

public enum PaymentStatus {
    FAIL(0),
    SUCCESS(1);

    private int value;
    private static Map map = new HashMap<>();

    static {
        for (PaymentStatus paymentStatus: PaymentStatus.values()) {
            map.put(paymentStatus.value, paymentStatus);
        }
    }

    PaymentStatus(int value) {
        this.value = value;
    }

    public static PaymentStatus valueOf(int i) {
        return (PaymentStatus) map.get(i);
    }

    public int getValue() {
        return value;
    }
}
