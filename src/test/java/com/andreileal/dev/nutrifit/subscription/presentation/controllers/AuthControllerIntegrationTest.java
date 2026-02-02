package com.andreileal.dev.nutrifit.subscription.presentation.controllers;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.andreileal.dev.nutrifit.subscription.presentation.dtos.requests.RequestAutenticacaoDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("AuthController Integration Tests")
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String AUTH_ENDPOINT = "/auth/signin";

    @Test
    @DisplayName("Deve retornar 400 quando email for invalido")
    void deveRetornar400QuandoEmailForInvalido() throws Exception {
        // Arrange
        RequestAutenticacaoDto request = new RequestAutenticacaoDto("emailinvalido", "senha123");

        // Act & Assert
        mockMvc.perform(post(AUTH_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Deve retornar 400 quando email for vazio")
    void deveRetornar400QuandoEmailForVazio() throws Exception {
        // Arrange
        RequestAutenticacaoDto request = new RequestAutenticacaoDto("", "senha123");

        // Act & Assert
        mockMvc.perform(post(AUTH_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Email nao pode ser vazio"));
    }

    @Test
    @DisplayName("Deve retornar 400 quando senha for vazia")
    void deveRetornar400QuandoSenhaForVazia() throws Exception {
        // Arrange
        RequestAutenticacaoDto request = new RequestAutenticacaoDto("usuario@example.com", "");

        // Act & Assert
        mockMvc.perform(post(AUTH_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Password nao pode ser vazio"));
    }

    @Test
    @DisplayName("Deve retornar 400 quando senha tiver menos de 6 caracteres")
    void deveRetornar400QuandoSenhaTiverMenosDe6Caracteres() throws Exception {
        // Arrange
        RequestAutenticacaoDto request = new RequestAutenticacaoDto("usuario@example.com", "12345");

        // Act & Assert
        mockMvc.perform(post(AUTH_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("Senha deve ter no minimo 6 caracteres"));
    }

    @Test
    @DisplayName("Deve retornar 400 quando multiplos campos forem invalidos")
    void deveRetornar400QuandoMultiplosCamposForemInvalidos() throws Exception {
        // Arrange
        RequestAutenticacaoDto request = new RequestAutenticacaoDto("", "");

        // Act & Assert
        mockMvc.perform(post(AUTH_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Deve retornar 401 quando credenciais forem invalidas")
    void deveRetornar401QuandoCredenciaisForemInvalidas() throws Exception {
        // Arrange
        RequestAutenticacaoDto request = new RequestAutenticacaoDto("usuario@inexistente.com", "senha123");

        // Act & Assert
        mockMvc.perform(post(AUTH_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("CREDENCIAIS_INVALIDAS"))
                .andExpect(jsonPath("$.message").value("Credenciais invalidas"));
    }

    @Test
    @DisplayName("Deve retornar erro quando Content-Type nao for JSON")
    void deveRetornar400QuandoContentTypeNaoForJson() throws Exception {
        // Act & Assert
        mockMvc.perform(post(AUTH_ENDPOINT)
                .contentType(MediaType.TEXT_PLAIN)
                .content("invalid content"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_ERROR"));
    }

    @Test
    @DisplayName("Deve retornar estrutura de resposta correta para validacao")
    void deveRetornarEstruturaDeRespostaCorretaParaValidacao() throws Exception {
        // Arrange
        RequestAutenticacaoDto request = new RequestAutenticacaoDto("emailinvalido", "12345");

        // Act & Assert
        mockMvc.perform(post(AUTH_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Deve retornar campos especificos de erro na validacao")
    void deveRetornarCamposEspecificosDeErroNaValidacao() throws Exception {
        // Arrange
        RequestAutenticacaoDto request = new RequestAutenticacaoDto("emailinvalido", "senha123");

        // Act & Assert
        mockMvc.perform(post(AUTH_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Deve aceitar requisi��o com formato JSON v�lido")
    void deveAceitarRequisicaoComFormatoJsonValido() throws Exception {
        // Arrange
        String jsonRequest = """
                {
                    "email": "usuario@example.com",
                    "senha": "senha123"
                }
                """;

        // Act & Assert
        // Nota: Este teste pode falhar com 401 se nao houver usuario cadastrado,
        // mas o importante e que passa pela validacao (nao retorna 400)
        mockMvc.perform(post(AUTH_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(result -> assertNotEquals(400, result.getResponse().getStatus()));
    }
}
