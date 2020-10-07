package ca.com.rlsp.delivery.order;

import org.bson.types.Decimal128;

public class Dish {

    public String name;

    public String description;

    //Decimal128 ==> usado por causa do MongoDB (ao inves do BgiDecimal
    public Decimal128 price;
}
