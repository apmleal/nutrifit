package com.andreileal.dev.nutrifit.subscription.application.usecases;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.andreileal.dev.nutrifit.subscription.application.dto.commands.LoginCommand;
import com.andreileal.dev.nutrifit.subscription.application.dto.results.LoginResult;
import com.andreileal.dev.nutrifit.subscription.domain.exceptions.CredenciaisInvalidasException;
import com.andreileal.dev.nutrifit.subscription.domain.exceptions.UsuarioNaoEncontradoException;
import com.andreileal.dev.nutrifit.subscription.domain.models.Role;
import com.andreileal.dev.nutrifit.subscription.domain.models.User;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Email;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Nome;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaHasheada;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaPlana;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.UserRepository;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.PasswordHasher;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoginUseCase Tests")
class LoginUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private LoginUseCase loginUseCase;

    private LoginCommand loginCommandValido;
    private User userMock;
    private Email emailValido;
    private Nome nomeValido;
    private SenhaHasheada senhaHasheadaValida;
    private List<UUID> tenantsValidos;

    @BeforeEach
    void setUp() {
        loginCommandValido = new LoginCommand("usuario@example.com", "senha123");
        emailValido = new Email("usuario@example.com");
        nomeValido = new Nome("João Silva");
        senhaHasheadaValida = new SenhaHasheada("$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy");
        tenantsValidos = List.of(UUID.randomUUID(), UUID.randomUUID());

        userMock = User.reconstituir(
                UUID.randomUUID(),
                emailValido,
                nomeValido,
                senhaHasheadaValida,
                tenantsValidos,
                Role.ADMINISTRATOR,
                true);
    }

    @Test
    @DisplayName("Deve executar login com sucesso")
    void deveExecutarLoginComSucesso() {
        // Arrange
        when(userRepository.findByEmailWithTenants(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(any(SenhaPlana.class), any(SenhaHasheada.class))).thenReturn(true);

        // Act
        LoginResult result = loginUseCase.execute(loginCommandValido);

        // Assert
        assertNotNull(result);
        assertEquals(userMock.getId(), result.userId());
        assertEquals(userMock.getEmail().valor(), result.email());
        assertEquals(userMock.getNome().valor(), result.nome());
        assertEquals(tenantsValidos, result.tenants());
        assertNotNull(result.tenants());
        assertEquals(2, result.tenants().size());

        verify(userRepository).findByEmailWithTenants(anyString());
        verify(passwordHasher).matches(any(SenhaPlana.class), eq(userMock.getSenhaHasheada()));
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
        // Arrange
        when(userRepository.findByEmailWithTenants(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        UsuarioNaoEncontradoException exception = assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> loginUseCase.execute(loginCommandValido));

        assertNotNull(exception);
        verify(userRepository).findByEmailWithTenants(anyString());
        verify(passwordHasher, never()).matches(any(), any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando senha for inválida")
    void deveLancarExcecaoQuandoSenhaForInvalida() {
        // Arrange
        when(userRepository.findByEmailWithTenants(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(any(SenhaPlana.class), any(SenhaHasheada.class))).thenReturn(false);

        // Act & Assert
        CredenciaisInvalidasException exception = assertThrows(
                CredenciaisInvalidasException.class,
                () -> loginUseCase.execute(loginCommandValido));

        assertNotNull(exception);
        verify(userRepository).findByEmailWithTenants(anyString());
        verify(passwordHasher).matches(any(SenhaPlana.class), eq(userMock.getSenhaHasheada()));
    }

    @Test
    @DisplayName("Deve criar Email VO corretamente a partir do comando")
    void deveCriarEmailVoCorretamenteAPartirDoComando() {
        // Arrange
        when(userRepository.findByEmailWithTenants(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(any(SenhaPlana.class), any(SenhaHasheada.class))).thenReturn(true);

        // Act
        loginUseCase.execute(loginCommandValido);

        // Assert
        verify(userRepository).findByEmailWithTenants("usuario@example.com");
    }

    @Test
    @DisplayName("Deve criar SenhaPlana VO corretamente a partir do comando")
    void deveCriarSenhaPlanaVoCorretamenteAPartirDoComando() {
        // Arrange
        when(userRepository.findByEmailWithTenants(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(argThat(senha -> senha.plena().equals("senha123")), any(SenhaHasheada.class)))
                .thenReturn(true);

        // Act
        loginUseCase.execute(loginCommandValido);

        // Assert
        verify(passwordHasher).matches(argThat(senha -> senha != null && senha.plena().equals("senha123")),
                any(SenhaHasheada.class));
    }

    @Test
    @DisplayName("Deve retornar lista de tenants do usuario")
    void deveRetornarListaDeTenantsDoUsuario() {
        // Arrange
        when(userRepository.findByEmailWithTenants(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(any(SenhaPlana.class), any(SenhaHasheada.class))).thenReturn(true);

        // Act
        LoginResult result = loginUseCase.execute(loginCommandValido);

        // Assert
        assertNotNull(result.tenants());
        assertEquals(tenantsValidos, result.tenants());
    }

    @Test
    @DisplayName("Deve retornar LoginResult com todos os campos preenchidos")
    void deveRetornarLoginResultComTodosOsCamposPreenchidos() {
        // Arrange
        when(userRepository.findByEmailWithTenants(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(any(SenhaPlana.class), any(SenhaHasheada.class))).thenReturn(true);

        // Act
        LoginResult result = loginUseCase.execute(loginCommandValido);

        // Assert
        assertAll(
                () -> assertNotNull(result.userId(), "UserId não deve ser nulo"),
                () -> assertNotNull(result.email(), "Email não deve ser nulo"),
                () -> assertNotNull(result.nome(), "Nome não deve ser nulo"),
                () -> assertNotNull(result.tenants(), "Tenants não deve ser nulo"),
                () -> assertFalse(result.email().isBlank(), "Email não deve ser vazio"),
                () -> assertFalse(result.nome().isBlank(), "Nome não deve ser vazio"),
                () -> assertFalse(result.tenants().isEmpty(), "Tenants não deve ser vazio"));
    }

    @Test
    @DisplayName("Deve normalizar email antes de buscar (lowercase)")
    void deveNormalizarEmailAntesDeBuscar() {
        // Arrange
        LoginCommand commandComEmailMaiusculo = new LoginCommand("USUARIO@EXAMPLE.COM", "senha123");
        when(userRepository.findByEmailWithTenants(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(any(SenhaPlana.class), any(SenhaHasheada.class))).thenReturn(true);

        // Act
        loginUseCase.execute(commandComEmailMaiusculo);

        // Assert
        verify(userRepository).findByEmailWithTenants("usuario@example.com");
    }
}
