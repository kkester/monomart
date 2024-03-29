package mart.mono.purchases;

import mart.mono.CommerceApplication;
import mart.mono.models.Purchase;
import mart.mono.services.PurchasesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CommerceApplication.class)
@AutoConfigureMockMvc
class PurchasesTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PurchasesService mockPurchaseService;

    @Test
    void purchases_list() throws Exception {
        when(this.mockPurchaseService.getAll()).thenReturn(singletonList(Purchase.builder().build()));

        mockMvc.perform(get("/api/purchases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)))
                .andExpect(jsonPath("$[0]", hasKey("items")));
    }
}
