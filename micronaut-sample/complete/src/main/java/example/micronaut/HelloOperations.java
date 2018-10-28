package example.micronaut;

import io.micronaut.http.annotation.Get;
import io.reactivex.Single;

public interface HelloOperations {

    @Get("/{name}")
    Single<String> hello(String name);
}