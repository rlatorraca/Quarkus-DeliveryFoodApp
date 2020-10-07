package ca.com.rlsp.delivery.model;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import ca.com.rlsp.delivery.dto.DishDTO;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;

import java.util.stream.StreamSupport;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;


@Path("/dishes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DishResources {

    @Inject
    PgPool pgPool; // DB Conexion

    @GET
    @APIResponse(responseCode = "200", 
    			 content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = DishDTO.class)))
    public Multi<DishDTO> getDishes() {
        return Dish.findAll(pgPool);
    }
}