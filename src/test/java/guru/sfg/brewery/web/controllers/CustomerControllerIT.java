package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by jt on 6/12/20.
 */
@SpringBootTest
public class CustomerControllerIT extends BaseIT {

    @Autowired
    CustomerRepository customerRepository;


    @DisplayName("/customers/find")
    @Nested
    class FindForm{
        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("guru.sfg.brewery.web.controllers.CustomerControllerIT#getStreamAdminCustomer")
        void findCustomersFormAUTH(String user, String pwd) throws Exception{
            mockMvc.perform(get("/customers/find")
                    .with(httpBasic(user, pwd)))
                    .andExpect(status().isOk())
                    .andExpect(view().name("customers/findCustomers"))
                    .andExpect(model().attributeExists("customer"));
        }

        @Test
        void findCustomersWithAnonymous() throws Exception{
            mockMvc.perform(get("/customers/find").with(anonymous()))
                    .andExpect(status().isUnauthorized());
        }

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @CsvSource({"user,password"})
        void findCustomersIsForbidden(String user, String pwd) throws Exception{
            mockMvc.perform(get("/customers/find")
                            .with(httpBasic(user, pwd)))
                    .andExpect(status().isForbidden());

        }
    }

}