package example.micronaut.bookrecommendation;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Maybe;

import javax.validation.constraints.NotBlank;

@Client(id = "bookinventory")
public interface BookInventoryClient extends BookInventoryOperations {

    @Get("/books/stock/{isbn}")
    Maybe<Boolean> stock(@NotBlank String isbn);
}