package service.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import service.model.dto.User;
import service.model.request.GetUserRequest;
import service.model.request.RegistryUserRequest;
import service.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/registry")
    public Mono<User> registryUser(@RequestBody RegistryUserRequest request) {
        return service.registryUser(request);
    }

    @GetMapping("/get")
    public Mono<User> getUser(@RequestBody GetUserRequest request) {
        return service.getUser(request);
    }
}
