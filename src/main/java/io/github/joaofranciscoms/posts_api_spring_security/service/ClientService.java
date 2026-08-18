package io.github.joaofranciscoms.posts_api_spring_security.service;

import io.github.joaofranciscoms.posts_api_spring_security.model.Client;
import io.github.joaofranciscoms.posts_api_spring_security.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client save(Client client){
        var senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);
        return repository.save(client);
    }

    public Client findById(String clientId){
        return repository.findByClientId(clientId);
    }
}
