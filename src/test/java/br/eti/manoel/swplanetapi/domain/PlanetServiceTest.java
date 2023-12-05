package br.eti.manoel.swplanetapi.domain;

import br.eti.manoel.swplanetapi.common.PlanetConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;


/**
 * Anotação - SpringBootTest - monta o contexto de aplicação do spring. Escaneia
 * os beans e deixa disponível para os testes. É possível especificar a
 * classe que desejamos tornar disponível (reduzindo custos), com o atributo
 * "classes".
 * <br>
 * Anotar a classe com @SpringBootTest
 * Criar os mocks das dependências (repositories) com @MockBean
 * Informar os parãmetros do mock para
 * <br>
 * define o mock do sistema sob teste, define o stub dele e executa a lógica
 * prinçipio AAA - arrange, act and assert
 * ======================================
 * Usando Mockito
 * <br>
 * Autowired não pode mais ser usado, porque nã há Spring Boot. Usa-se o @Injectmocks.
 * Injectmocks instancia o objeto e todas as dependẽncias, cirando mocks para elas. Ainda é preciso
 * definir (anotar) os mocks (anotar as dependências com @Mock).
 */
@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {

    //@Autowired
    @InjectMocks // instancia o planetservice
    private PlanetService planetService;

    /**
     * Cria um dublê de teste
     */
    //@MockBean
    @Mock
    private PlanetRepository planetRepository;

    // operacao_estado_retorno
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {

        // AAA
        // Arrange
        // dizendo para "quando" o método save for chamado, passando PLANET, deve retornar PLANET
        when(planetRepository.save(PlanetConstants.PLANET)).thenReturn(PlanetConstants.PLANET);

        // Act
        // sut - system under test
        Planet sut = planetService.create(PlanetConstants.PLANET);

        // Assert
        // implementar hahs and equals na classe Planet
        assertThat(sut).isEqualTo(PlanetConstants.PLANET);

    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        when(planetRepository.save(PlanetConstants.INVALID_PLANET)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> planetService.create(PlanetConstants.INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }
}
