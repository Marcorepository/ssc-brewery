package guru.sfg.brewery.web.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class BeerRestControllerIT extends BaseIT{


    @Test
    void findBeersWithAnonymous() throws Exception{
        mockMvc.perform(get("/api/v1/beer").with(anonymous()))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByIDWithAnonymous() throws Exception{
        mockMvc.perform(get("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311").with(anonymous()))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByIDWithAnonymousBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/beer/4711").with(anonymous()))
                .andExpect(status().isBadRequest());
    }
    @Test
    void findBeerByUpcWithAnonymous() throws Exception{
        mockMvc.perform(get("/api/v1/beerUpc/4711").with(anonymous()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerWithoutAuth() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311").with(anonymous()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteBeerWithApiKey() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .header("Api-Key", "spring").header("Api-Secret", "guru"))
                   //     .header("Api-Key", "myUser").header("Api-Secret", "password"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteBeerWithApiKeyFromParameters() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .param("apikey", "spring").param("apisecret", "guru"))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    void deleteBeerWithApiKeyBadCredetinials() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .header("Api-Key", "spring").header("Api-Secret", "guru2"))
                   //     .header("Api-Key", "myUser").header("Api-Secret", "password"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void deleteBeerWithApiKeyBadCredetinialsFromParameters() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .param("apikey", "spring").param("apisecret", "guru2"))
                .andExpect(status().isUnauthorized());
    }






}
