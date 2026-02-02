package com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.andreileal.dev.nutrifit.subscription.domain.exceptions.EmailInvalidoException;

@DisplayName("Email Value Object Tests")
class EmailTest {

    @Test
    @DisplayName("Deve criar email v�lido")
    void deveCriarEmailValido() {
        // Arrange & Act
        Email email = new Email("usuario@example.com");

        // Assert
        assertNotNull(email);
        assertEquals("usuario@example.com", email.valor());
    }

    @Test
    @DisplayName("Deve normalizar email para lowercase")
    void deveNormalizarEmailParaLowercase() {
        // Arrange & Act
        Email email = new Email("USUARIO@EXAMPLE.COM");

        // Assert
        assertEquals("usuario@example.com", email.valor());
    }

    @Test
    @DisplayName("Deve fazer trim do email")
    void deveFazerTrimDoEmail() {
        // Arrange & Act
        Email email = new Email("  usuario@example.com  ");

        // Assert
        assertEquals("usuario@example.com", email.valor());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "  ", "\t", "\n" })
    @DisplayName("Deve lan�ar exce��o para email nulo ou vazio")
    void deveLancarExcecaoParaEmailNuloOuVazio(String emailInvalido) {
        // Act & Assert
        EmailInvalidoException exception = assertThrows(
                EmailInvalidoException.class,
                () -> new Email(emailInvalido));

        assertEquals("Email invalido: Email nao pode ser vazio", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalido",
            "invalido@",
            "@example.com",
            "invalido@.com",
            "invalido@example",
            "inv@lido@example.com",
            "invalido @example.com",
            "invalido@exam ple.com"
    })
    @DisplayName("Deve lan�ar exce��o para formato de email inv�lido")
    void deveLancarExcecaoParaFormatoInvalido(String emailInvalido) {
        // Act & Assert
        EmailInvalidoException exception = assertThrows(
                EmailInvalidoException.class,
                () -> new Email(emailInvalido));

        assertTrue(exception.getMessage().contains("Email invalido") ||
                exception.getMessage().contains(emailInvalido));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "usuario@example.com",
            "user.name@example.com",
            "user+tag@example.com",
            "user_name@example.co.uk",
            "123@example.com",
            "user@sub.example.com"
    })
    @DisplayName("Deve aceitar emails v�lidos em v�rios formatos")
    void deveAceitarEmailsValidosEmVariosFormatos(String emailValido) {
        // Act
        Email email = new Email(emailValido);

        // Assert
        assertNotNull(email);
        assertEquals(emailValido.toLowerCase(), email.valor());
    }

    @Test
    @DisplayName("ToString deve retornar o valor do email")
    void toStringDeveRetornarValor() {
        // Arrange
        Email email = new Email("usuario@example.com");

        // Act & Assert
        assertEquals("usuario@example.com", email.toString());
    }

    @Test
    @DisplayName("Dois emails com mesmo valor devem ser iguais")
    void doisEmailsComMesmoValorDevemSerIguais() {
        // Arrange
        Email email1 = new Email("usuario@example.com");
        Email email2 = new Email("USUARIO@example.com");

        // Act & Assert
        assertEquals(email1, email2);
        assertEquals(email1.hashCode(), email2.hashCode());
    }
}
