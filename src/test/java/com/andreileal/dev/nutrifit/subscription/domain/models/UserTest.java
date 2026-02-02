package com.andreileal.dev.nutrifit.subscription.domain.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Email;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Nome;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaHasheada;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaPlana;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.PasswordHasher;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Entity Tests")
class UserTest {

    @Mock
    private PasswordHasher passwordHasher;

    private Email emailValido;
    private Nome nomeValido;
    private SenhaPlana senhaPlanaValida;
    private SenhaHasheada senhaHasheadaValida;

    @BeforeEach
    void setUp() {
        emailValido = new Email("usuario@example.com");
        nomeValido = new Nome("Joao Silva");
        senhaPlanaValida = new SenhaPlana("senha123");
        senhaHasheadaValida = new SenhaHasheada("$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy");
    }

    @Test
    @DisplayName("Deve criar novo usuario usando factory method criar()")
    void deveCriarNovoUsuarioUsandoFactoryMethodCriar() {
        // Arrange
        when(passwordHasher.hash(any(SenhaPlana.class))).thenReturn(senhaHasheadaValida);

        // Act
        User user = User.criar(emailValido, nomeValido, senhaPlanaValida, passwordHasher);

        // Assert
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(emailValido, user.getEmail());
        assertEquals(nomeValido, user.getNome());
        assertEquals(senhaHasheadaValida, user.getSenhaHasheada());
        verify(passwordHasher).hash(senhaPlanaValida);
    }

    @Test
    @DisplayName("Deve reconstituir usu�rio existente usando factory method reconstituir()")
    void devereconstituirUsuarioExistenteUsandoFactoryMethodReconstituir() {
        // Arrange
        UUID idExistente = UUID.randomUUID();

        // Act
        User user = User.reconstituir(idExistente, emailValido, nomeValido, senhaHasheadaValida);

        // Assert
        assertNotNull(user);
        assertEquals(idExistente, user.getId());
        assertEquals(emailValido, user.getEmail());
        assertEquals(nomeValido, user.getNome());
        assertEquals(senhaHasheadaValida, user.getSenhaHasheada());
    }

    @Test
    @DisplayName("Deve alterar email do usuario")
    void deveAlterarEmailDoUsuario() {
        // Arrange
        when(passwordHasher.hash(any(SenhaPlana.class))).thenReturn(senhaHasheadaValida);
        User user = User.criar(emailValido, nomeValido, senhaPlanaValida, passwordHasher);
        Email novoEmail = new Email("novoemail@example.com");

        // Act
        User userComEmailAlterado = user.alterarEmail(novoEmail);

        // Assert
        assertNotNull(userComEmailAlterado);
        assertEquals(novoEmail, userComEmailAlterado.getEmail());
        assertEquals(user.getId(), userComEmailAlterado.getId());
        assertEquals(user.getNome(), userComEmailAlterado.getNome());
        assertEquals(user.getSenhaHasheada(), userComEmailAlterado.getSenhaHasheada());
    }

    @Test
    @DisplayName("Deve atualizar nome do usuario")
    void deveAtualizarNomeDoUsuario() {
        // Arrange
        when(passwordHasher.hash(any(SenhaPlana.class))).thenReturn(senhaHasheadaValida);
        User user = User.criar(emailValido, nomeValido, senhaPlanaValida, passwordHasher);
        Nome novoNome = new Nome("Maria Santos");

        // Act
        User userComNomeAtualizado = user.atualizarNome(novoNome);

        // Assert
        assertNotNull(userComNomeAtualizado);
        assertEquals(novoNome, userComNomeAtualizado.getNome());
        assertEquals(user.getId(), userComNomeAtualizado.getId());
        assertEquals(user.getEmail(), userComNomeAtualizado.getEmail());
        assertEquals(user.getSenhaHasheada(), userComNomeAtualizado.getSenhaHasheada());
    }

    @Test
    @DisplayName("Deve validar senha corretamente")
    void deveValidarSenhaCorretamente() {
        // Arrange
        when(passwordHasher.hash(any(SenhaPlana.class))).thenReturn(senhaHasheadaValida);
        User user = User.criar(emailValido, nomeValido, senhaPlanaValida, passwordHasher);
        SenhaPlana senhaParaValidar = new SenhaPlana("senha123");
        when(passwordHasher.matches(senhaParaValidar, user.getSenhaHasheada())).thenReturn(true);

        // Act
        boolean isValid = user.isPasswordValid(senhaParaValidar, passwordHasher);

        // Assert
        assertTrue(isValid);
        verify(passwordHasher).matches(senhaParaValidar, user.getSenhaHasheada());
    }

    @Test
    @DisplayName("Deve retornar false para senha invalida")
    void deveRetornarFalseParaSenhaInvalida() {
        // Arrange
        when(passwordHasher.hash(any(SenhaPlana.class))).thenReturn(senhaHasheadaValida);
        User user = User.criar(emailValido, nomeValido, senhaPlanaValida, passwordHasher);
        SenhaPlana senhaIncorreta = new SenhaPlana("senhaerrada");
        when(passwordHasher.matches(senhaIncorreta, user.getSenhaHasheada())).thenReturn(false);

        // Act
        boolean isValid = user.isPasswordValid(senhaIncorreta, passwordHasher);

        // Assert
        assertFalse(isValid);
        verify(passwordHasher).matches(senhaIncorreta, user.getSenhaHasheada());
    }

    @Test
    @DisplayName("Deve garantir imutabilidade do ID")
    void deveGarantirImutabilidadeDoId() {
        // Arrange
        UUID idOriginal = UUID.randomUUID();
        User user = User.reconstituir(idOriginal, emailValido, nomeValido, senhaHasheadaValida);

        // Act
        User userAlterado = user.alterarEmail(new Email("outro@example.com"));

        // Assert
        assertEquals(idOriginal, user.getId());
        assertEquals(idOriginal, userAlterado.getId());
    }

    @Test
    @DisplayName("Deve garantir imutabilidade da senha hasheada")
    void deveGarantirImutabilidadeDaSenhaHasheada() {
        // Arrange
        when(passwordHasher.hash(any(SenhaPlana.class))).thenReturn(senhaHasheadaValida);
        User user = User.criar(emailValido, nomeValido, senhaPlanaValida, passwordHasher);
        SenhaHasheada senhaOriginal = user.getSenhaHasheada();

        // Act
        User userAlterado = user.alterarEmail(new Email("outro@example.com"));

        // Assert
        assertEquals(senhaOriginal, user.getSenhaHasheada());
        assertEquals(senhaOriginal, userAlterado.getSenhaHasheada());
    }

    @Test
    @DisplayName("Dois usu�rios com mesmo ID devem ser iguais")
    void doisUsuariosComMesmoIdDevemSerIguais() {
        // Arrange
        UUID id = UUID.randomUUID();
        User user1 = User.reconstituir(id, emailValido, nomeValido, senhaHasheadaValida);
        User user2 = User.reconstituir(id, new Email("outro@example.com"), new Nome("Outro Nome"), senhaHasheadaValida);

        // Act & Assert
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    @DisplayName("Dois usuarios com IDs diferentes nao devem ser iguais")
    void doisUsuariosComIdsDiferentesNaoDevemSerIguais() {
        // Arrange
        when(passwordHasher.hash(any(SenhaPlana.class))).thenReturn(senhaHasheadaValida);
        User user1 = User.criar(emailValido, nomeValido, senhaPlanaValida, passwordHasher);
        User user2 = User.criar(emailValido, nomeValido, senhaPlanaValida, passwordHasher);

        // Act & Assert
        assertNotEquals(user1, user2);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    @DisplayName("Alteracoes devem retornar nova instancia (imutabilidade)")
    void alteracoesDevemRetornarNovaInstancia() {
        // Arrange
        when(passwordHasher.hash(any(SenhaPlana.class))).thenReturn(senhaHasheadaValida);
        User userOriginal = User.criar(emailValido, nomeValido, senhaPlanaValida, passwordHasher);
        Email emailOriginal = userOriginal.getEmail();
        Nome nomeOriginal = userOriginal.getNome();

        // Act
        User userComEmailAlterado = userOriginal.alterarEmail(new Email("novo@example.com"));
        User userComNomeAlterado = userOriginal.atualizarNome(new Nome("Novo Nome"));

        // Assert
        assertNotSame(userOriginal, userComEmailAlterado);
        assertNotSame(userOriginal, userComNomeAlterado);
        assertEquals(emailOriginal, userOriginal.getEmail());
        assertEquals(nomeOriginal, userOriginal.getNome());
    }
}
