package com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects;

import com.andreileal.dev.nutrifit.subscription.domain.exceptions.SenhaInvalidaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SenhaPlana Value Object Tests")
class SenhaPlanaTest {

    @Test
    @DisplayName("Deve criar senha plana v�lida")
    void deveCriarSenhaPlanaValida() {
        // Arrange & Act
        SenhaPlana senha = new SenhaPlana("senha123");

        // Assert
        assertNotNull(senha);
        assertEquals("senha123", senha.plena());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "  ", "\t", "\n" })
    @DisplayName("Deve lan�ar exce��o para senha nula ou vazia")
    void deveLancarExcecaoParaSenhaNulaOuVazia(String senhaInvalida) {
        // Act & Assert
        SenhaInvalidaException exception = assertThrows(
                SenhaInvalidaException.class,
                () -> new SenhaPlana(senhaInvalida));

        assertEquals("Senha nao pode ser vazia", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "12345", "abc12", "senha" })
    @DisplayName("Deve lan�ar exce��o para senha menor que 6 caracteres")
    void deveLancarExcecaoParaSenhaMenorQue6Caracteres(String senhaCurta) {
        // Act & Assert
        SenhaInvalidaException exception = assertThrows(
                SenhaInvalidaException.class,
                () -> new SenhaPlana(senhaCurta));

        assertTrue(exception.getMessage().contains("no minimo 6 caracteres"));
    }

    @Test
    @DisplayName("Deve aceitar senha com tamanho m�nimo (6 caracteres)")
    void deveAceitarSenhaComTamanhoMinimo() {
        // Arrange & Act
        SenhaPlana senha = new SenhaPlana("123456");

        // Assert
        assertNotNull(senha);
        assertEquals("123456", senha.plena());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "senha123",
            "Senha@123",
            "MyP@ssw0rd!",
            "senhamuitolonga123456789"
    })
    @DisplayName("Deve aceitar senhas v�lidas")
    void deveAceitarSenhasValidas(String senhaValida) {
        // Act
        SenhaPlana senha = new SenhaPlana(senhaValida);

        // Assert
        assertNotNull(senha);
        assertEquals(senhaValida, senha.plena());
    }

    @Test
    @DisplayName("ToString deve retornar [PROTECTED]")
    void toStringDeveRetornarProtegido() {
        // Arrange
        SenhaPlana senha = new SenhaPlana("senha123");

        // Act & Assert
        assertEquals("[PROTECTED]", senha.toString());
        assertNotEquals("senha123", senha.toString());
    }

    @Test
    @DisplayName("Duas senhas com mesmo valor devem ser iguais")
    void duasSenhasComMesmoValorDevemSerIguais() {
        // Arrange
        SenhaPlana senha1 = new SenhaPlana("senha123");
        SenhaPlana senha2 = new SenhaPlana("senha123");

        // Act & Assert
        assertEquals(senha1, senha2);
        assertEquals(senha1.hashCode(), senha2.hashCode());
    }

    @Test
    @DisplayName("Senhas diferentes n�o devem ser iguais")
    void senhasDiferentesNaoDevemSerIguais() {
        // Arrange
        SenhaPlana senha1 = new SenhaPlana("senha123");
        SenhaPlana senha2 = new SenhaPlana("senha456");

        // Act & Assert
        assertNotEquals(senha1, senha2);
    }
}
