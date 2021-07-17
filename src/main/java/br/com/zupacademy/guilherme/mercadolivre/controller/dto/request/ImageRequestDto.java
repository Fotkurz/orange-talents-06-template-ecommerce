package br.com.zupacademy.guilherme.mercadolivre.controller.dto.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ImageRequestDto {

    @Size(min = 1)
    private List<MultipartFile> images = new ArrayList<>();

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public List<MultipartFile> getImages() {
        return this.images;
    }
}
