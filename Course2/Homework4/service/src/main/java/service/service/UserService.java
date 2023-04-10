package service.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import persistence.entity.UserEntity;
import persistence.repository.UserRepository;
import reactor.core.publisher.Mono;
import service.model.dto.User;
import service.model.request.GetUserRequest;
import service.model.request.RegistryUserRequest;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final ModelMapper mapper;

    public Mono<User> registryUser(RegistryUserRequest request) {
        return repository
                .save(UserEntity
                        .builder()
                        .name(request.getName())
                        .currency(request.getPreferred())
                        .build())
                .map(entity -> mapper.map(entity, User.class));
    }

    public Mono<User> getUser(GetUserRequest request) {
        return repository
                .findById(request.getId())
                .map(entity -> mapper.map(entity, User.class));
    }
}
