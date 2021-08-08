package coding.test.controller.paymentController;

import coding.test.dto.PaymentNotificationTestDTO;
import coding.test.model.FpResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Sql(['../../datasets/sql/cleanDB.sql'])
@AutoConfigureMockMvc
@SpringBootTest
public class NotificationTest {

    private final static String ENDPOINT = "/payment/notification";

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    private MvcResult performTest(String reqBody) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .content(reqBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        return result;
    }

    @DisplayName("Missing request body")
    @Test
    public void test10001() throws Exception {
        MvcResult result = performTest("");

        MockHttpServletResponse response = result.getResponse();
        String body = result.getResponse().getContentAsString();
        FpResponseDTO responseDTO = mapper.readValue(body, FpResponseDTO.class);
        assertEquals(500, response.getStatus());
        assertEquals(false, responseDTO.getSuccess());
        assertEquals("internal.error", ((Map) ((List) responseDTO.getResult()).get(0)).get("code"));
        assertEquals("Required request body is missing: public coding.test.model.FpResponseDTO coding.test.controller.PaymentController.paymentNotification(coding.test.model.PaymentNotificationDTO)", ((Map) ((List) responseDTO.getResult()).get(0)).get("message"));
    }

    @DisplayName("test01")
    @Test
    public void test20001() throws Exception {
        PaymentNotificationTestDTO dto = new PaymentNotificationTestDTO();
        dto.transactions.user_id = null;
        String reqBody = new ObjectMapper().writeValueAsString(dto);
        MvcResult result = performTest(reqBody);

        MockHttpServletResponse response = result.getResponse();
        String body = result.getResponse().getContentAsString();
        FpResponseDTO responseDTO = mapper.readValue(body, FpResponseDTO.class);
        assertEquals(200, response.getStatus());
        assertEquals(false, responseDTO.getSuccess());
        assertEquals("internal.error", ((Map) ((List) responseDTO.getResult()).get(0)).get("code"));
        assertEquals("Required request body is missing: public coding.test.model.FpResponseDTO coding.test.controller.PaymentController.paymentNotification(coding.test.model.PaymentNotificationDTO)", ((Map) ((List) responseDTO.getResult()).get(0)).get("message"));
    }
}