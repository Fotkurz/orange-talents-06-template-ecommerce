package br.com.zupacademy.guilherme.mercadolivre.controller.dto.response;

import br.com.zupacademy.guilherme.mercadolivre.domain.Opinion;

public class OpinionResponseDto {

    private Integer rating;
    private String title;
    private String description;

    public OpinionResponseDto(Opinion opinion) {
        this.title = opinion.getTitle();
        this.rating = opinion.getRating();
        this.description = opinion.getDescription();
    }

    public Integer getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
