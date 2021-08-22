package br.com.zupacademy.guilherme.mercadolivre.product.domain;

import br.com.zupacademy.guilherme.mercadolivre.product.dto.CharacteristicRequestDto;
import br.com.zupacademy.guilherme.mercadolivre.user.domain.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @NotNull
    @Min(0)
    @PositiveOrZero
    private Integer quantity;
    @NotBlank
    @Length(max = 1000)
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Characteristic> characteristics;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<ProductImages> images;

    @NotNull
    @ManyToOne
    private Category category;

    @NotNull
    private final LocalDateTime registerTime = LocalDateTime.now();

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Opinion> opinions;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductQuestion> questions;

    public Product() {
    }

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

    public User getOwner() {
        return this.user;
    }

    public String getName() {
        return this.name;
    }

    public Long getId() {
        return id;
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

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public Set<ProductImages> getImages() {
        return images;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public User getUser() {
        return user;
    }

    public List<Opinion> getOpinions() {
        return opinions;
    }

    public List<ProductQuestion> getQuestions() {
        return questions;
    }

    public String getCategoryName() {
        return this.category.getName();
    }

    public Double ratingAverage() {
        int sum;
        List<Integer> ratings = opinions.stream().map(Opinion::getRating).collect(Collectors.toList());
        Integer elements = ratingNumber(opinions);
        sum = ratings.stream().mapToInt(i -> i).sum();
        return (double) sum / elements;
    }

    public Integer ratingNumber(List<Opinion> opinions) {
        return opinions.size();
    }

    public boolean stockCheck(Integer neededNumber) {
        if (this.quantity < neededNumber) return false;
        return true;
    }

    public void decreaseQuantity(Integer bought) {
        if(stockCheck(bought)) this.quantity -= bought;
    }
}
