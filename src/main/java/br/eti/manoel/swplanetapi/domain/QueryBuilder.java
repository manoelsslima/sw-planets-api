package br.eti.manoel.swplanetapi.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

/**
 * Classe para criar queries dinâmicas.
 */
public class QueryBuilder {
    public static Example<Planet> makeQuery(Planet planet) {
        // withIgnoreNullValues() - ignora os valores dos filtros que foram omitidos
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
        return Example.of(planet, exampleMatcher);
    }
}
