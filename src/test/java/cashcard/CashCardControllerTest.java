package cashcard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CashCardControllerTest {
    @MockBean
    private CashCardService cashCardService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreate() throws Exception {
        mockMvc
                .perform(post("/cash-card"))
                .andExpect(status().isCreated())
                .andReturn();

        verify(cashCardService).create();
    }

    @Test
    public void shouldLoad() throws Exception {
        mockMvc
                .perform(
                        post("/cash-card/1/load")
                                .content("10.00")
                                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        verify(cashCardService).load(1, new BigDecimal("10.00"));
    }

    @Test
    public void shouldSpend() throws Exception {
        mockMvc
                .perform(
                        post("/cash-card/1/spend")
                                .content("5.00")
                                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        verify(cashCardService).spend(1, new BigDecimal("5.00"));
    }
}
