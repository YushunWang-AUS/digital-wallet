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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(value = {"../../../../cleanDB.sql", "../../../../init_data.sql"})
@AutoConfigureMockMvc
@SpringBootTest
public class NotificationTest {

    /*TODO: 1. each API field null,EMPTY,BLANK value check
            2. each API field min/max/over-max length check
            3. date/email format check
            4. enum field value check (type, debit_credit)
            5. user doesn't exist check
    */

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

    @DisplayName("user_id: null")
    @Test
    public void test20001() throws Exception {
        PaymentNotificationTestDTO dto = new PaymentNotificationTestDTO();
        dto.transactions.user_id = null;
        String reqBody = new ObjectMapper().writeValueAsString(dto);
        MvcResult result = performTest(reqBody);

        MockHttpServletResponse response = result.getResponse();
        String body = result.getResponse().getContentAsString();
        FpResponseDTO responseDTO = mapper.readValue(body, FpResponseDTO.class);
        assertEquals(400, response.getStatus());
        assertEquals(false, responseDTO.getSuccess());
        assertEquals("transactions.user_id.invalid", ((Map) ((List) responseDTO.getResult()).get(0)).get("code"));
        assertEquals("transactions.user_id must not be blank", ((Map) ((List) responseDTO.getResult()).get(0)).get("message"));
    }

    @DisplayName("user_id: EMPTY")
    @Test
    public void test20002() throws Exception {
        PaymentNotificationTestDTO dto = new PaymentNotificationTestDTO();
        dto.transactions.user_id = "";
        String reqBody = new ObjectMapper().writeValueAsString(dto);
        MvcResult result = performTest(reqBody);

        MockHttpServletResponse response = result.getResponse();
        String body = result.getResponse().getContentAsString();
        FpResponseDTO responseDTO = mapper.readValue(body, FpResponseDTO.class);
        assertEquals(400, response.getStatus());
        assertEquals(false, responseDTO.getSuccess());
        assertEquals("transactions.user_id.invalid", ((Map) ((List) responseDTO.getResult()).get(0)).get("code"));
        assertEquals("transactions.user_id must not be blank", ((Map) ((List) responseDTO.getResult()).get(0)).get("message"));
    }

    @DisplayName("user_id: BLANK")
    @Test
    public void test20003() throws Exception {
        PaymentNotificationTestDTO dto = new PaymentNotificationTestDTO();
        dto.transactions.user_id = " ";
        String reqBody = new ObjectMapper().writeValueAsString(dto);
        MvcResult result = performTest(reqBody);

        MockHttpServletResponse response = result.getResponse();
        String body = result.getResponse().getContentAsString();
        FpResponseDTO responseDTO = mapper.readValue(body, FpResponseDTO.class);
        assertEquals(400, response.getStatus());
        assertEquals(false, responseDTO.getSuccess());
        assertEquals("transactions.user_id.invalid", ((Map) ((List) responseDTO.getResult()).get(0)).get("code"));
        assertEquals("transactions.user_id must not be blank", ((Map) ((List) responseDTO.getResult()).get(0)).get("message"));
    }

    @DisplayName("1.create wallet account. 2.create transaction, 3.create 2nd transaction")
    @Test
    public void test30001() throws Exception {
        PaymentNotificationTestDTO dto = new PaymentNotificationTestDTO();
        String reqBody = new ObjectMapper().writeValueAsString(dto);
        MvcResult result = performTest(reqBody);

        // create transaction
        MockHttpServletResponse response = result.getResponse();
        String body = result.getResponse().getContentAsString();
        FpResponseDTO responseDTO = mapper.readValue(body, FpResponseDTO.class);
        assertEquals(200, response.getStatus());
        assertEquals(true, responseDTO.getSuccess());

        // create 2nd transaction
        PaymentNotificationTestDTO dto2 = new PaymentNotificationTestDTO();
        dto2.transactions.id = "xxxx-1111";
        dto2.transactions.amount = "50.00";
        dto2.transactions.debit_credit = "DEBIT";
        reqBody = new ObjectMapper().writeValueAsString(dto2);
        result = performTest(reqBody);

        response = result.getResponse();
        body = result.getResponse().getContentAsString();
        responseDTO = mapper.readValue(body, FpResponseDTO.class);
        assertEquals(200, response.getStatus());
        assertEquals(true, responseDTO.getSuccess());
    }

    @DisplayName("Duplicated transaction")
    @Test
    public void test90001() throws Exception {
        PaymentNotificationTestDTO dto = new PaymentNotificationTestDTO();
        String reqBody = new ObjectMapper().writeValueAsString(dto);
        MvcResult result = performTest(reqBody);

        // create transaction
        MockHttpServletResponse response = result.getResponse();
        String body = result.getResponse().getContentAsString();
        FpResponseDTO responseDTO = mapper.readValue(body, FpResponseDTO.class);
        assertEquals(200, response.getStatus());
        assertEquals(true, responseDTO.getSuccess());

        // create same transaction
        result = performTest(reqBody);
        response = result.getResponse();
        body = result.getResponse().getContentAsString();
        responseDTO = mapper.readValue(body, FpResponseDTO.class);
        assertEquals(200, response.getStatus());
        assertEquals(false, responseDTO.getSuccess());
        assertEquals("internal.error", ((Map) ((List) responseDTO.getResult()).get(0)).get("code"));
        assertEquals("Transaction [49f1cb10-0202-0138-225b-028e897a70a1] already exists.", ((Map) ((List) responseDTO.getResult()).get(0)).get("message"));

    }
}