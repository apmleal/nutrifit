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

import com.andreileal.dev.nutrifit.subscription.domain.exceptions.NomeInvalidoException;

@DisplayName("Nome Value Object Tests")
class NomeTest {

    @Test
    @DisplayName("Deve criar nome v�lido")
    void deveCriarNomeValido() {
        // Arrange & Act
        Nome nome = new Nome("Jo�o Silva");

        // Assert
        assertNotNull(nome);
        assertEquals("Jo�o Silva", nome.valor());
    }

    @Test
    @DisplayName("Deve fazer trim do nome")
    void deveFazerTrimDoNome() {
        // Arrange & Act
        Nome nome = new Nome("  Jo�o Silva  ");

        // Assert
        assertEquals("Jo�o Silva", nome.valor());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "  ", "\t", "\n" })
    @DisplayName("Deve lan�ar exce��o para nome nulo ou vazio")
    void deveLancarExcecaoParaNomeNuloOuVazio(String nomeInvalido) {
        // Act & Assert
        NomeInvalidoException exception = assertThrows(
                NomeInvalidoException.class,
                () -> new Nome(nomeInvalido));

        assertEquals("Nome nao pode ser vazio", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "Jo", "AB" })
    @DisplayName("Deve lan�ar exce��o para nome muito curto")
    void deveLancarExcecaoParaNomeMuitoCurto(String nomeCurto) {
        // Act & Assert
        NomeInvalidoException exception = assertThrows(
                NomeInvalidoException.class,
                () -> new Nome(nomeCurto));

        assertTrue(exception.getMessage().contains("no minimo 3 caracteres"));
    }

    @Test
    @DisplayName("Deve lan�ar exce��o para nome muito longo")
    void deveLancarExcecaoParaNomeMuitoLongo() {
        // Arrange
        String nomeLongo = "A".repeat(201);

        // Act & Assert
        NomeInvalidoException exception = assertThrows(
                NomeInvalidoException.class,
                () -> new Nome(nomeLongo));

        assertTrue(exception.getMessage().contains("no maximo 200 caracteres"));
    }

    @Test
    @DisplayName("Deve aceitar nome com tamanho m�nimo (3 caracteres)")
    void deveAceitarNomeComTamanhoMinimo() {
        // Arrange & Act
        Nome nome = new Nome("Ana");

        // Assert
        assertNotNull(nome);
        assertEquals("Ana", nome.valor());
    }

    @Test
    @DisplayName("Deve aceitar nome com tamanho m�ximo (200 caracteres)")
    void deveAceitarNomeComTamanhoMaximo() {
        // Arrange
        String nomeMaximo = "A".repeat(200);

        // Act
        Nome nome = new Nome(nomeMaximo);

        // Assert
        assertNotNull(nome);
        assertEquals(200, nome.valor().length());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Jo�o Silva",
            "Maria de Souza Santos",
            "Jos� da Silva",
            "Ana",
            "Carlos Eduardo Pereira"
    })
    @DisplayName("Deve aceitar nomes v�lidos")
    void deveAceitarNomesValidos(String nomeValido) {
        // Act
        Nome nome = new Nome(nomeValido);

        // Assert
        assertNotNull(nome);
        assertEquals(nomeValido, nome.valor());
    }

    @Test
    @DisplayName("ToString deve retornar o valor do nome")
    void toStringDeveRetornarValor() {
        // Arrange
        Nome nome = new Nome("Jo�o Silva");

        // Act & Assert
        assertEquals("Jo�o Silva", nome.toString());
    }

    @Test
    @DisplayName("Dois nomes com mesmo valor devem ser iguais")
    void doisNomesComMesmoValorDevemSerIguais() {
        // Arrange
        Nome nome1 = new Nome("Jo�o Silva");
        Nome nome2 = new Nome("  Jo�o Silva  ");

        // Act & Assert
        assertEquals(nome1, nome2);
        assertEquals(nome1.hashCode(), nome2.hashCode());
    }
}
