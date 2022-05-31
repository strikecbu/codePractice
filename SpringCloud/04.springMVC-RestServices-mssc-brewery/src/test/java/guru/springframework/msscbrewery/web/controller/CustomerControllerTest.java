package guru.springframework.msscbrewery.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ObjectMapper objectMapper;

    CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customerDto = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Andy")
                .build();
    }

    @Test
    void getCustomer() throws Exception {

        given(customerService.getCustomerById(any())).willReturn(customerDto);

        mockMvc.perform(get("/api/v1/customer/" + customerDto.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String contentAsString = result.getResponse()
                            .getContentAsString();
                    System.out.printf("Response: %s%n", contentAsString);
                })
                .andExpect(jsonPath("$.name", Is.is(customerDto.getName())));

    }

    @Test
    void postCustomer() throws Exception {

        given(customerService.saveCustomer(any())).willReturn(customerDto);

        mockMvc.perform(post("/api/v1/customer").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(result -> {
                    HashMap<String, List<String>> headers = result.getResponse()
                            .getHeaderNames()
                            .stream()
                            .reduce(new HashMap<>(), (map, name) -> {
                                map.put(name,
                                        result.getResponse()
                                                .getHeaders(name));
                                return map;
                            }, (m1, m2) -> {
                                m1.putAll(m2);
                                return m1;
                            });
                    System.out.printf("Headers: %s%n", headers);
                });

    }

    @Test
    void putCustomer() throws Exception {
        mockMvc.perform(put("/api/v1/customer/" + customerDto.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isNoContent());
        then(customerService).should()
                .updateCustomer(any(), any());
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/v1/customer/" + customerDto.getId()))
                .andExpect(status().isNoContent());

        then(customerService).should()
                .deleteCustomer(any());
    }

}
