package br.com.zupacademy.guilherme.mercadolivre.domain;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_product_images")
public class ProductImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @URL
    private String url;

    @ManyToOne
    @NotNull
    private Product product;

    @Deprecated
    public ProductImages() {
    }

    public ProductImages(Product product, String url) {
        this.url = url;
        this.product = product;
    }

    public String getUrl() {
        return this.url;
    }
}
