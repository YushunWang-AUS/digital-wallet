package coding.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class BaseTest {

    @Autowired
    WebApplicationContext context;

    protected MockMvc mockMvc;

    protected WebTestClient client;

//    public BaseTest() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//        client = WebTestClient.bindToApplicationContext(context).build();
//    }

    MockMvc getMockMvc() {
        return MockMvcBuilders.webAppContextSetup(context).build();
    }

    WebTestClient getClient() {
        return WebTestClient.bindToApplicationContext(context).build();
    }

}
