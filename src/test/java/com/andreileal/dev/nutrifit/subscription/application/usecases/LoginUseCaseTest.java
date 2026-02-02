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

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.andreileal.dev.nutrifit.subscription.application.usecases.dto.commands.LoginCommand;
import com.andreileal.dev.nutrifit.subscription.application.usecases.dto.results.LoginResult;
import com.andreileal.dev.nutrifit.subscription.domain.exceptions.CredenciaisInvalidasException;
import com.andreileal.dev.nutrifit.subscription.domain.exceptions.UsuarioNaoEncontradoException;
import com.andreileal.dev.nutrifit.subscription.domain.models.User;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.AccessToken;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Email;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Nome;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaHasheada;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaPlana;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.UserRepository;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.PasswordHasher;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.TokenGenerator;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoginUseCase Tests")
class LoginUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenGenerator tokenGenerator;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private LoginUseCase loginUseCase;

    private LoginCommand loginCommandValido;
    private User userMock;
    private Email emailValido;
    private Nome nomeValido;
    private SenhaHasheada senhaHasheadaValida;
    private AccessToken tokenGerado;

    @BeforeEach
    void setUp() {
        loginCommandValido = new LoginCommand("usuario@example.com", "senha123");
        emailValido = new Email("usuario@example.com");
        nomeValido = new Nome("Jo�o Silva");
        senhaHasheadaValida = new SenhaHasheada("$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy");
        tokenGerado = new AccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");

        userMock = User.reconstituir(
                UUID.randomUUID(),
                emailValido,
                nomeValido,
                senhaHasheadaValida);
    }

    @Test
    @DisplayName("Deve executar login com sucesso")
    void deveExecutarLoginComSucesso() {
        // Arrange
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(any(SenhaPlana.class), any(SenhaHasheada.class))).thenReturn(true);
        when(tokenGenerator.gerar(anyString())).thenReturn(tokenGerado);

        // Act
        LoginResult result = loginUseCase.execute(loginCommandValido);

        // Assert
        assertNotNull(result);
        assertEquals(tokenGerado.valor(), result.token());
        assertEquals(userMock.getId(), result.userId());
        assertEquals(userMock.getEmail().valor(), result.email());
        assertEquals(userMock.getNome().valor(), result.nome());

        verify(userRepository).findUserByEmail(anyString());
        verify(passwordHasher).matches(any(SenhaPlana.class), eq(userMock.getSenhaHasheada()));
        verify(tokenGenerator).gerar(anyString());
    }

    @Test
    @DisplayName("Deve lan�ar exce��o quando usu�rio n�o for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
        // Arrange
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        UsuarioNaoEncontradoException exception = assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> loginUseCase.execute(loginCommandValido));

        assertNotNull(exception);
        verify(userRepository).findUserByEmail(anyString());
        verify(passwordHasher, never()).matches(any(), any());
        verify(tokenGenerator, never()).gerar(anyString());
    }

    @Test
    @DisplayName("Deve lan�ar exce��o quando senha for inv�lida")
    void deveLancarExcecaoQuandoSenhaForInvalida() {
        // Arrange
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(any(SenhaPlana.class), any(SenhaHasheada.class))).thenReturn(false);

        // Act & Assert
        CredenciaisInvalidasException exception = assertThrows(
                CredenciaisInvalidasException.class,
                () -> loginUseCase.execute(loginCommandValido));

        assertNotNull(exception);
        verify(userRepository).findUserByEmail(anyString());
        verify(passwordHasher).matches(any(SenhaPlana.class), eq(userMock.getSenhaHasheada()));
        verify(tokenGenerator, never()).gerar(anyString());
    }

    @Test
    @DisplayName("Deve criar Email VO corretamente a partir do comando")
    void deveCriarEmailVoCorretamenteAPartirDoComando() {
        // Arrange
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(any(SenhaPlana.class), any(SenhaHasheada.class))).thenReturn(true);
        when(tokenGenerator.gerar(anyString())).thenReturn(tokenGerado);

        // Act
        loginUseCase.execute(loginCommandValido);

        // Assert
        verify(userRepository).findUserByEmail("usuario@example.com");
    }

    @Test
    @DisplayName("Deve criar SenhaPlana VO corretamente a partir do comando")
    void deveCriarSenhaPlanaVoCorretamenteAPartirDoComando() {
        // Arrange
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(argThat(senha -> senha.plena().equals("senha123")), any(SenhaHasheada.class)))
                .thenReturn(true);
        when(tokenGenerator.gerar(anyString())).thenReturn(tokenGerado);

        // Act
        loginUseCase.execute(loginCommandValido);

        // Assert
        verify(passwordHasher).matches(argThat(senha -> senha != null && senha.plena().equals("senha123")),
                any(SenhaHasheada.class));
    }

    @Test
    @DisplayName("Deve chamar tokenGenerator com o email correto")
    void deveChamarTokenGeneratorComOUsuarioCorreto() {
        // Arrange
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(any(SenhaPlana.class), any(SenhaHasheada.class))).thenReturn(true);
        when(tokenGenerator.gerar(anyString())).thenReturn(tokenGerado);

        // Act
        loginUseCase.execute(loginCommandValido);

        // Assert
        verify(tokenGenerator).gerar("usuario@example.com");
    }

    @Test
    @DisplayName("Deve retornar LoginResult com todos os campos preenchidos")
    void deveRetornarLoginResultComTodosOsCamposPreenchidos() {
        // Arrange
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(any(SenhaPlana.class), any(SenhaHasheada.class))).thenReturn(true);
        when(tokenGenerator.gerar(anyString())).thenReturn(tokenGerado);

        // Act
        LoginResult result = loginUseCase.execute(loginCommandValido);

        // Assert
        assertAll(
                () -> assertNotNull(result.token(), "Token n�o deve ser nulo"),
                () -> assertNotNull(result.userId(), "UserId n�o deve ser nulo"),
                () -> assertNotNull(result.email(), "Email n�o deve ser nulo"),
                () -> assertNotNull(result.nome(), "Nome n�o deve ser nulo"),
                () -> assertFalse(result.token().isBlank(), "Token n�o deve ser vazio"),
                () -> assertFalse(result.email().isBlank(), "Email n�o deve ser vazio"),
                () -> assertFalse(result.nome().isBlank(), "Nome n�o deve ser vazio"));
    }

    @Test
    @DisplayName("Deve normalizar email antes de buscar (lowercase)")
    void deveNormalizarEmailAntesDeBuscar() {
        // Arrange
        LoginCommand commandComEmailMaiusculo = new LoginCommand("USUARIO@EXAMPLE.COM", "senha123");
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(userMock));
        when(passwordHasher.matches(any(SenhaPlana.class), any(SenhaHasheada.class))).thenReturn(true);
        when(tokenGenerator.gerar(anyString())).thenReturn(tokenGerado);

        // Act
        loginUseCase.execute(commandComEmailMaiusculo);

        // Assert
        verify(userRepository).findUserByEmail("usuario@example.com");
    }
}
