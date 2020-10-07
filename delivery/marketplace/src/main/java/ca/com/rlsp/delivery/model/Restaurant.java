package ca.com.rlsp.delivery.model;

import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

public class Restaurant {

	public Long id;

    public String name;

    public Mapposition mapPosition;
    
    @Override
    public String toString() {
        return "Restaurant [id=" + id + ", name=" + name + ", MapPosition=" + mapPosition+ "]";
    }
    
    public void persist(PgPool pgPool) {
        pgPool.preparedQuery("insert into mapposition (id, latitude, longetude) values ($1, $2, $3)")
        		.execute(Tuple.of(mapPosition.id, mapPosition.latitude, mapPosition.longetude))
        		.await().indefinitely();

        pgPool.preparedQuery("insert into restaurant (id, name, mapposition_id) values ($1, $2, $3)")
        		.execute(Tuple.of(id, name, mapPosition.id))
        		.await().indefinitely();

    }
}
