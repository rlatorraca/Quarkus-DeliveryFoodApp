package com.rlsp.graphql;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;


@GraphQLApi
public class DishResource {

	@Query("getDishes")
	@Description("Return all dishes avaliable")
    public List<Prato> bustarTodosPratos() {
        Prato prato = new Prato();
        prato.setNome("feijao");
        prato.setDescricao("prato tipico de algum lugar");
        prato.setValor(BigDecimal.TEN);
        List<Prato> lista = Collections.singletonList(prato);
        return lista;
    }

    @Query("getDisheById")
    @Description("Return a dish by ID")
    public Prato bustarPrato(@Name("id") Integer id) {
        System.out.println("ID: " + id);
        Prato prato = new Prato();
        prato.setNome("feijao");
        prato.setDescricao("prato tipico de algum lugar");
        prato.setValor(BigDecimal.TEN);
        return prato;
    }

    @Name("restaurante")
    public Restaurant buscarRestaurante(@Source Prato prato) {
        Restaurant restaurante = new Restaurant();
        restaurante.setOwner("Jorge Baleiro");
        restaurante.setName("Mangueirao");
        return restaurante;
    }

    @Mutation // Altera; Inclui ou Exluir
    @Description("Change restaurant anem")
    public Restaurant alterar(Restaurant restaurant) {
        System.out.println(restaurant.getName());
        return restaurant;
    }
}