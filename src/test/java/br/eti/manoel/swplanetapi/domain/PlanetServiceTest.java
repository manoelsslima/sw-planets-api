package br.eti.manoel.swplanetapi.domain;

import br.eti.manoel.swplanetapi.common.PlanetConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
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

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        when(planetRepository.findById(anyLong())).thenReturn(Optional.of(PlanetConstants.PLANET));
        Optional<Planet> sut = planetService.get(1L);
        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PlanetConstants.PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty() {
        when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Planet> sut = planetService.get(1L);
        assertThat(sut).isEmpty();
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() {
        when(planetRepository.findByName(PlanetConstants.PLANET.getName())).thenReturn(Optional.of(PlanetConstants.PLANET));
        Optional<Planet> sut = planetService.getByName(PlanetConstants.PLANET.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PlanetConstants.PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsEmpty() {
        when(planetRepository.findByName(anyString())).thenReturn(Optional.empty());
        Optional<Planet> sut = planetService.getByName(anyString());

        assertThat(sut).isEmpty();
    }

    @Test
    public void listPlanets_ReturnsAllPlanets() {
        List<Planet> planets = new ArrayList<>() {{
            add(PlanetConstants.PLANET);
        }};
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(PlanetConstants.PLANET.getClimate(), PlanetConstants.PLANET.getTerrain()));
        when(planetRepository.findAll(query)).thenReturn(planets);
        List<Planet> sut = planetService.list(PlanetConstants.PLANET.getTerrain(), PlanetConstants.PLANET.getClimate());
        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PlanetConstants.PLANET);
    }

    @Test
    public void listPlanets_ReturnsNoPlanets() {
        when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());
        List<Planet> sut = planetService.list(PlanetConstants.PLANET.getTerrain(), PlanetConstants.PLANET.getClimate());
        assertThat(sut).isEmpty();
    }

    @Test
    public void removePlanet_WithExistingId_doesNotThrowAnyException() {
        // verifica se nenhuma exceção foi lançada
        // afira se o código passado no parâmetro não lança exceção
        assertThatCode(() -> planetService.remove(1L)).doesNotThrowAnyException();
    }

    @Test
    public void removePlanet_WithUnexistingId_throwsException() {
        // inverte a lógica para: será lançada uma exceção quando, do repositório passado,
        // for método for chamado o método deleteById
        // utilizar quando for fazer stubs de métodos void
        doThrow(new RuntimeException()).when(planetRepository).deleteById(99L);
        assertThatThrownBy(() -> planetService.remove(99L)).isInstanceOf(RuntimeException.class);
    }
}
