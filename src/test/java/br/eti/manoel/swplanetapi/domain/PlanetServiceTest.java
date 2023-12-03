package br.eti.manoel.swplanetapi.domain;

import br.eti.manoel.swplanetapi.common.PlanetConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
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
 */
@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {

    @Autowired
    private PlanetService planetService;

    /**
     * Cria um dublê de teste
     */
    @MockBean
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
}
