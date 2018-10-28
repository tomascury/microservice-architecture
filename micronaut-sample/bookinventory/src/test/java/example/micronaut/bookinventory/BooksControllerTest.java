package example.micronaut.bookinventory;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

public class BooksControllerTest {

    private static EmbeddedServer server;
    private static RxHttpClient rxHttpClient;

    @BeforeClass
    public static void setupServer() {
        server = ApplicationContext.run(EmbeddedServer.class);
        rxHttpClient = server
                .getApplicationContext()
                .createBean(RxHttpClient.class, server.getURL());
    }

    @AfterClass
    public static void stopServer() {
        if (server != null) {
            server.stop();
        }
        if (rxHttpClient != null) {
            rxHttpClient.stop();
        }
    }

    @Test
    public void testBooksController() {
        HttpResponse<Boolean> rsp = rxHttpClient.toBlocking().exchange(HttpRequest.GET("/books/stock/1491950358"), Boolean.class);
        assertEquals(rsp.status(), HttpStatus.OK);
        assertTrue(rsp.body());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testBooksControllerWithNonExistingIsbn() {
        thrown.expect(HttpClientResponseException.class);
        thrown.expect(hasProperty("response", hasProperty("status", is(HttpStatus.NOT_FOUND))));
        rxHttpClient.toBlocking().exchange(HttpRequest.GET("/books/stock/XXXXX"), Boolean.class);
    }
}