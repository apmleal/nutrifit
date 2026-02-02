package com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("AccessToken Value Object Tests")
class AccessTokenTest {

    @Test
    @DisplayName("Deve criar access token v�lido")
    void deveCriarAccessTokenValido() {
        // Arrange & Act
        AccessToken token = new AccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");

        // Assert
        assertNotNull(token);
        assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9", token.valor());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "  ", "\t", "\n" })
    @DisplayName("Deve lan�ar exce��o para token nulo ou vazio")
    void deveLancarExcecaoParaTokenNuloOuVazio(String tokenInvalido) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new AccessToken(tokenInvalido));

        assertEquals("Token nao pode ser vazio", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIn0",
            "simple-token-123",
            "Bearer eyJhbGciOiJIUzI1NiJ9",
            "abc123xyz"
    })
    @DisplayName("Deve aceitar tokens v�lidos em v�rios formatos")
    void deveAceitarTokensValidosEmVariosFormatos(String tokenValido) {
        // Act
        AccessToken token = new AccessToken(tokenValido);

        // Assert
        assertNotNull(token);
        assertEquals(tokenValido, token.valor());
    }

    @Test
    @DisplayName("ToString deve retornar o valor do token")
    void toStringDeveRetornarValor() {
        // Arrange
        String tokenStr = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
        AccessToken token = new AccessToken(tokenStr);

        // Act & Assert
        assertEquals(tokenStr, token.toString());
    }

    @Test
    @DisplayName("Dois tokens com mesmo valor devem ser iguais")
    void doisTokensComMesmoValorDevemSerIguais() {
        // Arrange
        AccessToken token1 = new AccessToken("token123");
        AccessToken token2 = new AccessToken("token123");

        // Act & Assert
        assertEquals(token1, token2);
        assertEquals(token1.hashCode(), token2.hashCode());
    }

    @Test
    @DisplayName("Tokens diferentes n�o devem ser iguais")
    void tokensDiferentesNaoDevemSerIguais() {
        // Arrange
        AccessToken token1 = new AccessToken("token123");
        AccessToken token2 = new AccessToken("token456");

        // Act & Assert
        assertNotEquals(token1, token2);
    }
}
