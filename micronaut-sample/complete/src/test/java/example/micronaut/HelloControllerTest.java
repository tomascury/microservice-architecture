package example.micronaut;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HelloControllerTest {

    private static EmbeddedServer server;
    private static HttpClient client;
    private static HelloControllerClient helloControllerClient;

    @BeforeClass
    public static void setupServer() {

        server = ApplicationContext.run(EmbeddedServer.class);

        client = server
                .getApplicationContext()
                .createBean(HttpClient.class, server.getURL());

        helloControllerClient = server
                .getApplicationContext()
                .getBean(HelloControllerClient.class);
    }

    @AfterClass
    public static void stopServer() {

        if (server != null) {
            server.stop();
        }
        if (client != null) {
            client.stop();
        }
    }

    @Test
    public void testHello() throws Exception {

        String input = "Tomas";
        HttpRequest request = HttpRequest.GET("/hello/" + input);
        String body = client.toBlocking().retrieve(request);
        assertNotNull(body);
        assertEquals(
                body,
                "Hello, " + input + "!"
        );
    }

    @Test
    public void shouldReturnHello() {

        String input = "Tomas";
        String response = helloControllerClient.hello(input).blockingGet();
        assertEquals(response, "Hello, "+ input +"!");
    }
}