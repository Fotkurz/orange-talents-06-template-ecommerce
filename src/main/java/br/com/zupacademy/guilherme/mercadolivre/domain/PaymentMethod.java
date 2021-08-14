package br.com.zupacademy.guilherme.mercadolivre.domain;

public enum PaymentMethod {
    PAYPAL,
    PAGSEGURO;

    public static boolean isField(String paymentMethod) {
        PaymentMethod[] list = values();
        for(PaymentMethod p : list) {
            if(p.toString().equals(paymentMethod)) return true;
        }
        return false;
    }

}
