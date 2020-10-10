package ca.com.rlsp.delivery.registration.panache;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;

import ca.com.rlsp.delivery.registration.model.Restaurant;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;

public class PanacheQueries {

    public void exemplosSelects() {
        // -- Classe --

        //findAll

        PanacheQuery<Restaurant> findAll = Restaurant.findAll();
        Restaurant.findAll(Sort.by("name").and("id", Direction.Ascending));

        PanacheQuery<Restaurant> page1 = findAll.page(Page.ofSize(10)); // Pagina de 10 em 10
        PanacheQuery<Restaurant> page2 = findAll.page(Page.of(3, 10));  // Pega a terceira pagina, de 10 em 10

        //find sem sort (NAO PRECISA @Transactio no metodo

        Map<String, Object> params = new HashMap<>();
        params.put("name", "");
        Restaurant.find("select r from Restaurant where name = :name", params);

        String name = "";
        Restaurant.find("select r from Restaurant where name = $1", name);

        Restaurant.find("select r from Restaurant where name = :name and id = :id",
                Parameters.with("name", "").and("id", 1L));

        //Atalho para findAll.stream, mas precisa de transacao se nao o cursor pode fechar antes

        Restaurant.stream("select r from Restaurant where name = :name", params);

        Restaurant.stream("select r from Restaurant where name = $1", name);

        Restaurant.stream("select r from Restaurant where name = :name and id = :id",
                Parameters.with("name", "").and("id", 1L));

        //find by id

        Restaurant findById = Restaurant.findById(1L);

        //Persist

        Restaurant.persist(findById, findById);

        //Delete

        Restaurant.delete("delete Restaurant where name = :name", params);

        Restaurant.delete("delete Restaurant where name = $1", name);

        Restaurant.delete("nome = :name and id = :id",
                Parameters.with("name", "").and("id", 1L));

        //Update

        Restaurant.update("update Restaurant where name = :name", params);

        Restaurant.update("update Restaurant where name = $1", name);

        //Count

        Restaurant.count();

        //-- Métodos de objeto--

        Restaurant restaurant = new Restaurant();

        restaurant.persist();
        restaurant.persistAndFlush();

        restaurant.isPersistent();

        restaurant.delete();

    }


    /**
    @Entity //(Sugestão do Quarkus)
    class MinhaEntidade1 extends PanacheEntity {
        public String name;
    }

    @Entity // Esse NAO define um ID
    class MinhaEntidade2 extends PanacheEntityBase {
        public String name;
    }

    @Entity
    class MinhaEntidade3 {
        public String name;
    }

    @ApplicationScoped
    class MeuRepositorio implements PanacheRepository<MinhaEntidade3> {

    }
*/
}
