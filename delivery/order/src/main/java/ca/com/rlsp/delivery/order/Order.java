package ca.com.rlsp.delivery.order;

import java.util.List;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

@MongoEntity(collection = "orders", database = "order")
public class Order extends PanacheMongoEntity {

    public String client;

    public List<Dish> dishes;

    public Restaurant restaurant;

    public String entregador;

    public Mapposition mapposition;

}