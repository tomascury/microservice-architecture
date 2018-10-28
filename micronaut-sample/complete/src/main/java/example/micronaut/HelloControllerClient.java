package example.micronaut;

import io.micronaut.http.client.annotation.Client;

@Client("/hello")
public interface HelloControllerClient extends HelloOperations {
}