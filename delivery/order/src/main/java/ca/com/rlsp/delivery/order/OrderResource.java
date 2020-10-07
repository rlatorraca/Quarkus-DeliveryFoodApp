package ca.com.rlsp.delivery.order;


import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.vertx.core.Vertx;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.mutiny.core.eventbus.EventBus;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

	@Inject
    Vertx vertx;

    @Inject
    EventBus eventBus;

    void startup(@Observes Router router) {
        router.route("/positions*").handler(positionHandler());
    }

    private SockJSHandler positionHandler() {
        SockJSHandler handler = SockJSHandler.create(vertx);
        PermittedOptions permitted = new PermittedOptions();
        permitted.setAddress("newPosition");
        SockJSBridgeOptions bridgeOptions = new SockJSBridgeOptions().addOutboundPermitted(permitted);
        handler.bridge(bridgeOptions);        
        return handler;
    }

    @GET
    public List<PanacheMongoEntityBase> hello() {
        return Order.listAll();
    }

    @POST
    @Path("{idOrder}/position")
    public Order novaLocalizacao(@PathParam("idOrder") String idOrder, Mapposition position) {
    	Order order = Order.findById(new ObjectId(idOrder));

    	order.mapposition = position;
        String json = JsonbBuilder.create().toJson(position);
        eventBus.sendAndForget("newPosition", json);
        order.persistOrUpdate();
        return order;
    }
}
