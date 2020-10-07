package ca.com.rlsp.delivery.model;

import java.util.ArrayList;
import java.util.List;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

public class DishCart {

  public String client;

  public Long dish;
  
  public static Uni<Long> save(PgPool client, String cliente, Long prato) {
      return client.preparedQuery("INSERT INTO dish_client (client, dish) VALUES ($1, $2) RETURNING (client)").execute(
              Tuple.of(cliente, prato))

              .map(pgRowSet -> pgRowSet.iterator().next().getLong("cliente"));  
  }

  public static Uni<List<DishCart>> findCarrinho(PgPool client, String cliente) {
	  return client.preparedQuery("select * from dish_client where client = $1 ").execute(Tuple.of(cliente))
              .map(pgRowSet -> {
                  List<DishCart> list = new ArrayList<>(pgRowSet.size());
                  for (Row row : pgRowSet) {
                      list.add(toPratoCarrinho(row));
                  }
                  return list;
              });
  }

  private static DishCart toPratoCarrinho(Row row) {
	  DishCart pc = new DishCart();
      pc.client = row.getString("client");
      pc.dish = row.getLong("dish");
      return pc;
  }

  public static Uni<Boolean> delete(PgPool client, String cliente) {
	  return client.preparedQuery("DELETE FROM dish_client WHERE client = $1").execute(Tuple.of(cliente))
              .map(pgRowSet -> pgRowSet.rowCount() == 1);

  }
}
