package example.micronaut;

import io.micronaut.http.annotation.Get;
import io.reactivex.Single;

public interface HelloOperations {

    @Get("/hello/{name}")
    Single<String> hello(String name);
}