package service.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import service.model.dto.Product;
import service.model.request.GetProductRequest;
import service.model.request.RegisterProductRequest;
import service.service.ProductService;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping("/register")
    public Mono<Product> registerProduct(@RequestBody RegisterProductRequest request) {
        return service.registerProduct(request);
    }

    @GetMapping("/get")
    public Mono<Product> getProduct(@RequestBody GetProductRequest request) {
        return service.getProduct(request);
    }
}
