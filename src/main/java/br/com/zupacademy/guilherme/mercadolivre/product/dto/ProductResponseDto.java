package br.com.zupacademy.guilherme.mercadolivre.product.dto;

import br.com.zupacademy.guilherme.mercadolivre.product.domain.Characteristic;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.Product;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.ProductImages;
import br.com.zupacademy.guilherme.mercadolivre.product.domain.ProductQuestion;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductResponseDto {

    private Set<String> images; // Link para imagens
    private String nome; // Nome do produto
    private BigDecimal value; // Preço do produto
    private Map<String, String> characteristics; // Características
    private String description; // Descrição
    private Double ratingAverage; // Média de notas
    private Integer ratingNumber; // Numero total de notas
    private List<OpinionResponseDto> opinions; // Opiniões
    private List<String> questions; // Perguntas
    private Integer quantity; // Notei que na página de exemplo também tem a quantidade de unidades disponíveis

    public ProductResponseDto(Product product) {
        this.nome = product.getName();
        this.value = product.getValue();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.characteristics = extractCharacteristics(product);
        this.images = product.getImages().stream().map(ProductImages::getUrl).collect(Collectors.toSet());
        this.ratingAverage = product.ratingAverage();
        this.ratingNumber = product.ratingNumber(product.getOpinions());
        this.questions = product.getQuestions().stream().map(ProductQuestion::getTitle).collect(Collectors.toList());
        this.opinions = convertOpinions(product);
    }

    private List<OpinionResponseDto> convertOpinions(Product product) {
        return product.getOpinions().stream().map(OpinionResponseDto::new).collect(Collectors.toList());
    }

    private Map<String, String> extractCharacteristics(Product product) {
        return product.getCharacteristics()
                .stream().collect(Collectors.toMap(Characteristic::getName, Characteristic::getDescription));
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getCharacteristics() {
        return characteristics;
    }

    public Set<String> getImages() {
        return images;
    }

    public Double getRatingAverage() {
        return ratingAverage;
    }

    public Integer getRatingNumber() {
        return ratingNumber;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public List<OpinionResponseDto> getOpinions() {
        return opinions;
    }
}
