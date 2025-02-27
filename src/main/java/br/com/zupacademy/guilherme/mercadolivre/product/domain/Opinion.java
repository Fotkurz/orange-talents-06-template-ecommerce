package br.com.zupacademy.guilherme.mercadolivre.product.domain;

import br.com.zupacademy.guilherme.mercadolivre.user.domain.User;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "tbl_opinions")
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Range(min = 1, max = 5)
    @Positive
    private Integer rating;
    @NotBlank
    private String title;
    @NotBlank
    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    @NotNull
    private Product product;
    @ManyToOne
    @NotNull
    private User user;

    public Opinion() {
    }

    public Opinion(Integer rating, String title, String description, Product product, User user) {
        this.rating = rating;
        this.title = title;
        this.description = description;
        this.product = product;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public Integer getRating() {
        return this.rating;
    }

    public String getDescription() {
        return this.description;
    }
}
