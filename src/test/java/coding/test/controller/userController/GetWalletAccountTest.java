package coding.test.controller.userController;

import coding.test.model.FpResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(value = {"../../../../cleanDB.sql", "../../../../walletAccountExist.sql"})
@AutoConfigureMockMvc
@SpringBootTest
public class GetWalletAccountTest {

    private final static String ENDPOINT = "/user/wallet-account/449416d8-ec3c-4c0b-a326-e2cfaadaa3a6";

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    private MvcResult performTest(String reqBody) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT)
                .content(reqBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        return result;
    }

    @DisplayName("Get wallet account")
    @Test
    public void test10001() throws Exception {
        MvcResult result = performTest("");

        MockHttpServletResponse response = result.getResponse();
        String body = result.getResponse().getContentAsString();
        FpResponseDTO responseDTO = mapper.readValue(body, FpResponseDTO.class);
        assertEquals(200, response.getStatus());
        assertEquals(true, responseDTO.getSuccess());
        Map account = (Map) responseDTO.getResult();
        assertEquals(1, account.get("walletAccountId"));
        assertEquals(999, account.get("userId"));
        assertEquals("449416d8-ec3c-4c0b-a326-e2cfaadaa3a6", account.get("userReference"));
        assertEquals("Neol", account.get("firstName"));
        assertEquals("Buyer", account.get("lastName"));
        assertEquals("neol@gmail.com", account.get("email"));
        assertEquals("neol", account.get("username"));

    }
}