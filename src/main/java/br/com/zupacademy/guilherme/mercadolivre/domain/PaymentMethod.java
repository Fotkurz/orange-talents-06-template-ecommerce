package br.com.zupacademy.guilherme.mercadolivre.domain;

import br.com.zupacademy.guilherme.mercadolivre.controller.dto.PaymentReturnDto;

import java.net.URI;
import java.util.UUID;

public enum PaymentMethod {
    PAYPAL,
    PAGSEGURO;

    private String url;

    static {
        PAYPAL.url = "www.paypal.com?buyerId=";
        PAGSEGURO.url = "www.pagseguro.com?returnId=";
    }

    public static boolean isField(String paymentMethod) {
        PaymentMethod[] list = values();
        for(PaymentMethod p : list) {
            if(p.toString().equals(paymentMethod)) return true;
        }
        return false;
    }

    public static PaymentReturnDto sendRedirect(Long idCheckout, PaymentMethod paymentMethod) {
        String urlRedirect = "payment-processor";
        URI location;
        String idService = UUID.randomUUID().toString();
        if(paymentMethod.equals(PAYPAL)) {
            int status = 1;
            String idPayment = UUID.randomUUID().toString();
            // Check arbitrário para forçar uma falha
            if(idCheckout % 3 == 0) status = 0;
            location = URI.create(PAYPAL.url + idCheckout +
                    "&idCheckout=" + idCheckout +
                    "&status=" + PaymentStatus.valueOf(status)+
                    "&redirectUrl=" + urlRedirect +
                    "?idGateway=" + idService +
                    "&idCheckout=" + idCheckout +
                    "&status=" + PaymentStatus.valueOf(status));
            return new PaymentReturnDto(location, idService, idCheckout, PaymentStatus.valueOf(status));

        } else {
            String status = "SUCCESS";
            String idPayment = UUID.randomUUID().toString();
            // Check arbitrário para forçar uma falha
            if(idCheckout % 3 == 0) status = "FAIL";
            location = URI.create(PAYPAL.url + idCheckout +
                    "&idCheckout=" + idCheckout +
                    "&status=" + PaymentStatus.valueOf(status)+
                    "&redirectUrl=" + urlRedirect +
                    "?idGateway=" + idService +
                    "&idCheckout=" + idCheckout +
                    "&status=" + PaymentStatus.valueOf(status));
            return new PaymentReturnDto(location, idService, idCheckout, PaymentStatus.valueOf(status));    }
        }

}


