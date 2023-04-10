package service.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import persistence.common.Currency;
import persistence.entity.ProductEntity;
import persistence.repository.ProductRepository;
import persistence.repository.UserRepository;
import reactor.core.publisher.Mono;
import service.model.dto.Product;
import service.model.request.GetProductRequest;
import service.model.request.RegisterProductRequest;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    private Integer convert(Integer cost, Currency start, Currency finish) {
        switch (start) {
            case DOLLAR -> cost *= 70;
            case EURO -> cost *= 80;
            case RUBLES -> cost *= 1;
        }
        switch (finish) {
            case DOLLAR -> cost /= 70;
            case EURO -> cost /= 80;
            case RUBLES -> cost /= 1;
        }
        return cost;
    }

    public Mono<Product> registerProduct(RegisterProductRequest request) {
        return userRepository
                .findById(request.getUserId())
                .flatMap(user -> {
                    var cost = convert(request.getCost(), user.getCurrency(), Currency.RUBLES);
                    return productRepository.save(ProductEntity
                                    .builder()
                                    .name(request.getProductName())
                                    .cost(cost)
                                    .build())
                            .map(productEntity -> {
                                Product product = mapper.map(productEntity, Product.class);
                                product.setCost(request.getCost());
                                product.setCurrency(user.getCurrency());
                                return product;
                            });
                });
    }

    public Mono<Product> getProduct(GetProductRequest request) {
        return userRepository
                .findById(request.getUserId())
                .flatMap(user -> productRepository
                        .findById(request.getProductId())
                        .map(productEntity -> {
                            Product product = mapper.map(productEntity, Product.class);
                            product.setCost(convert(product.getCost(), Currency.RUBLES, user.getCurrency()));
                            product.setCurrency(user.getCurrency());
                            return product;
                        }));
    }
}
