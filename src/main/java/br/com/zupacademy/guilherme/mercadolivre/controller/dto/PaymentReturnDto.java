package br.com.zupacademy.guilherme.mercadolivre.controller.dto;

import br.com.zupacademy.guilherme.mercadolivre.domain.PaymentStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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

    public URI buildReturn(UriComponentsBuilder uriComponentsBuilder) {
        String query = urlLocation.getQuery();
        String newUri = query.substring(query.indexOf("&redirectUrl=") + "&redirectUrl=".length());
        URI returnUri = URI.create(newUri);
        return returnUri;
    }


}
