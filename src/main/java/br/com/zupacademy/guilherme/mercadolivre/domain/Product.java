package br.com.zupacademy.guilherme.mercadolivre.domain;

import br.com.zupacademy.guilherme.mercadolivre.controller.dto.request.CharacteristicRequestDto;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tbl_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    @Positive
    private BigDecimal value;
    @NotNull @Min(0) @Positive
    private Integer quantity;
    @NotBlank @Length(max = 1000)
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST) @Column(name = "characteristics")
    private List<Characteristic> characteristics;
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<ProductImages> images;
    @NotNull @ManyToOne
    private Category category;
    @NotNull
    private LocalDateTime registerTime = LocalDateTime.now();

    @OneToOne
    private User user;
    public Product() {}

    public Product(@NotBlank String name, @NotNull @Positive BigDecimal value,
                   @NotNull @Min(0) Integer quantity,
                   @NotBlank @Length(max = 1000) String description,
                   @NotNull Category category,
                   @NotNull List<CharacteristicRequestDto> characteristics,
                   @NotNull User user) {
        this.name = name;
        this.value = value;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
        this.characteristics = characteristics.stream().map(characteristic -> characteristic.toModel(this)).collect(Collectors.toList());
        this.user = user;
    }

    public void setImagesLinks(Set<String> urls) {
        this.images = urls.stream().map(url -> new ProductImages(this, url)).collect(Collectors.toSet());
    }

    public boolean isOwner(String login) {
        return this.user.getUsername().contentEquals(login);
    }
}
