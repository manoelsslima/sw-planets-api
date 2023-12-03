package br.eti.manoel.swplanetapi.domain;

import br.eti.manoel.swplanetapi.common.PlanetConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @SpringBootTest - monta o contexto de aplicação do spring. Escaneia
 * os beans e deixa disponível para os testes. É possível especificar a
 * classe que desejamos tornar disponível (reduzindo custos), com o atributo
 * "classes".
 */
@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {

    @Autowired
    private PlanetService planetService;

    // operacao_estado_retorno
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {

        // sut - system under test
        Planet sut = planetService.create(PlanetConstants.PLANET);
        // implementar hahs and equals na classe Planet
        assertThat(sut).isEqualTo(PlanetConstants.PLANET);

    }
}
