package com.vkncode.budget.controller;

import com.vkncode.budget.domain.repository.BudgetRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BudgetControllerTest {

    @Autowired
    private BudgetRepository repository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void budgetSave() throws Exception {
        MvcResult result;

        String json = "{\"totalAmount\": 100, \"spentAmount\": 100, \"destination\": \"EDUCATION\", \"origin\": \"COUNTY\"}";

        result = mvc.perform(post("/budget")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        JSONObject budgetSaved = new JSONObject(result.getResponse().getContentAsString());

        result = mvc.perform(get("/budget/" + budgetSaved.get("id"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject budgetSearched = new JSONObject(result.getResponse().getContentAsString());

        assertNotNull(budgetSaved);
        assertNotNull(budgetSearched);
        assertEquals(budgetSaved.get("id"), budgetSearched.get("id"));
        assertEquals(budgetSaved.get("spentAmount"), budgetSearched.get("spentAmount"));
        assertEquals(budgetSaved.get("totalAmount"), budgetSearched.get("totalAmount"));
    }
}
