package br.com.zupacademy.guilherme.mercadolivre.checkout.dto;

import br.com.zupacademy.guilherme.mercadolivre.checkout.controller.PaymentStatus;
import br.com.zupacademy.guilherme.mercadolivre.checkout.domain.Checkout;
import br.com.zupacademy.guilherme.mercadolivre.checkout.domain.Transaction;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PaymentReturnDto {

    private URI urlLocation;
    private String idGateway;
    private Long idCheckout;
    private PaymentStatus paymentStatus;

    public PaymentReturnDto(URI urlLocation, String idGateway, Long idCheckout, PaymentStatus paymentStatus) {
        this.urlLocation = urlLocation;
        this.idGateway = idGateway;
        this.idCheckout = idCheckout;
        this.paymentStatus = paymentStatus;
    }

    public URI getUrlLocation() {
        return urlLocation;
    }

    public URI buildReturn() {
        String query = urlLocation.getQuery();
        String newUri = query.substring(query.indexOf("&redirectUrl=") + "&redirectUrl=".length());

        URI returnUri = URI.create(newUri);

        return returnUri;
    }

    public Map<String, String> readParameters() {
        Map<String, String> values = new HashMap<>();
        String returnUri = buildReturn().toString();
        String[] parameters = returnUri.split("[?]");
        String[] keyAndValues = parameters[1].split("&");
        Arrays.stream(keyAndValues).forEach(s -> {
            String[] splitString = s.split("=");
            values.put(splitString[0], splitString[1]);
        });
        return values;
    }

    public Transaction toTransaction(Checkout checkout) {
        Map<String, String> values = readParameters();
        if (checkout.getId().equals(Long.valueOf(values.get("idCheckout")))) {
            return new Transaction(PaymentStatus.valueOf(values.get("status")),
                                    checkout,
                                    values.get("idGateway"),
                                    buildReturn().toString());
        }
        return null;
    }
}
