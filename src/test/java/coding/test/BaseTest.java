package coding.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMockMvc
//@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class BaseTest {

    @Autowired
    WebApplicationContext context;

    protected static MockMvc mockMvc;

    protected static WebTestClient client;

    protected MockMvc getMockMvc() {
        return MockMvcBuilders.webAppContextSetup(context).build();
    }

    protected WebTestClient getClient() {
        return WebTestClient.bindToApplicationContext(context).build();
    }

}
