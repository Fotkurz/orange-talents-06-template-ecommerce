package br.com.zupacademy.guilherme.mercadolivre.product.domain;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Profile("dev")
public class DevImageUpload {

    private Set<String> urls = new HashSet<>();

    public DevImageUpload(List<MultipartFile> images) {
        images.forEach(i -> {
            urls.add("http://s3.amazonaws.com/mercadolivre/" + i.getOriginalFilename());
        });
    }

    public Set<String> getUrls() {
        return urls;
    }
}
