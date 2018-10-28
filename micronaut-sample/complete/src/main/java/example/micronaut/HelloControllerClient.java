package example.micronaut;

import io.micronaut.http.client.annotation.Client;

@Client("/")
public interface HelloControllerClient extends HelloOperations {
}