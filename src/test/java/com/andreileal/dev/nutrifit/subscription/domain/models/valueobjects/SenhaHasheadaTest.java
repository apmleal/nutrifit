package com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.andreileal.dev.nutrifit.subscription.domain.exceptions.SenhaInvalidaException;

@DisplayName("SenhaHasheada Value Object Tests")
class SenhaHasheadaTest {

    private static final String HASH_BCRYPT_VALIDO = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";

    @Test
    @DisplayName("Deve criar senha hasheada v�lida com bcrypt")
    void deveCriarSenhaHasheadaValidaComBcrypt() {
        // Arrange & Act
        SenhaHasheada senha = new SenhaHasheada(HASH_BCRYPT_VALIDO);

        // Assert
        assertNotNull(senha);
        assertEquals(HASH_BCRYPT_VALIDO, senha.hash());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "  ", "\t", "\n" })
    @DisplayName("Deve lan�ar exce��o para hash nulo ou vazio")
    void deveLancarExcecaoParaHashNuloOuVazio(String hashInvalido) {
        // Act & Assert
        SenhaInvalidaException exception = assertThrows(
                SenhaInvalidaException.class,
                () -> new SenhaHasheada(hashInvalido));

        assertEquals("Hash da senha nao pode ser vazio", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "senhaemtextoplano",
            "123456",
            "hash_invalido",
            "$1a$10$invalido",
            "bcrypt_fake"
    })
    @DisplayName("Deve lan�ar exce��o para hash que n�o � bcrypt")
    void deveLancarExcecaoParaHashQueNaoEBcrypt(String hashInvalido) {
        // Act & Assert
        SenhaInvalidaException exception = assertThrows(
                SenhaInvalidaException.class,
                () -> new SenhaHasheada(hashInvalido));

        assertTrue(exception.getMessage().contains("formato bcrypt") ||
                exception.getMessage().contains("$2a$") ||
                exception.getMessage().contains("$2b$") ||
                exception.getMessage().contains("$2y$"));
    }

    @Test
    @DisplayName("Deve lan�ar exce��o para hash bcrypt muito curto")
    void deveLancarExcecaoParaHashBcryptMuitoCurto() {
        // Arrange
        String hashCurto = "$2a$10$curto";

        // Act & Assert
        SenhaInvalidaException exception = assertThrows(
                SenhaInvalidaException.class,
                () -> new SenhaHasheada(hashCurto));

        assertTrue(exception.getMessage().contains("no minimo 59 caracteres"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy",
            "$2b$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy",
            "$2y$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy"
    })
    @DisplayName("Deve aceitar hashes bcrypt v�lidos ($2a$, $2b$, $2y$)")
    void deveAceitarHashesBcryptValidos(String hashValido) {
        // Act
        SenhaHasheada senha = new SenhaHasheada(hashValido);

        // Assert
        assertNotNull(senha);
        assertEquals(hashValido, senha.hash());
    }

    @Test
    @DisplayName("ToString deve retornar [PROTECTED]")
    void toStringDeveRetornarProtegido() {
        // Arrange
        SenhaHasheada senha = new SenhaHasheada(HASH_BCRYPT_VALIDO);

        // Act & Assert
        assertEquals("[PROTECTED]", senha.toString());
        assertNotEquals(HASH_BCRYPT_VALIDO, senha.toString());
    }

    @Test
    @DisplayName("Duas senhas com mesmo hash devem ser iguais")
    void duasSenhasComMesmoHashDevemSerIguais() {
        // Arrange
        SenhaHasheada senha1 = new SenhaHasheada(HASH_BCRYPT_VALIDO);
        SenhaHasheada senha2 = new SenhaHasheada(HASH_BCRYPT_VALIDO);

        // Act & Assert
        assertEquals(senha1, senha2);
        assertEquals(senha1.hashCode(), senha2.hashCode());
    }

    @Test
    @DisplayName("Deve validar tamanho exato do bcrypt (60 caracteres)")
    void deveValidarTamanhoExatoDoBcrypt() {
        // Arrange
        String hashBcrypt60Chars = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";

        // Act
        SenhaHasheada senha = new SenhaHasheada(hashBcrypt60Chars);

        // Assert
        assertNotNull(senha);
        assertEquals(60, senha.hash().length());
    }

    @Test
    @DisplayName("Deve rejeitar senha em texto plano")
    void deveRejeitarSenhaEmTextoPlano() {
        // Arrange
        String senhaTextoPlano = "minhaSenha123!";

        // Act & Assert
        SenhaInvalidaException exception = assertThrows(
                SenhaInvalidaException.class,
                () -> new SenhaHasheada(senhaTextoPlano));

        assertTrue(exception.getMessage().contains("formato bcrypt"));
    }
}
