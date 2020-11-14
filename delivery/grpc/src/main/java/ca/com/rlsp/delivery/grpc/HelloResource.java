package ca.com.rlsp.delivery.grpc;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import ca.com.rlsp.proto.HelloRequest;
import ca.com.rlsp.proto.HelloResponse;
import ca.com.rlsp.proto.HelloServiceGrpc.HelloServiceBlockingStub;
import io.quarkus.grpc.runtime.annotations.GrpcService;

@Path("/hello")
public class HelloResource {

	@Inject
	@GrpcService(value = "Hello-Service")
	HelloServiceBlockingStub helloService;
	
	@GET
	@Path("/block/{nome}")
	public String getBlock(@PathParam("nome") String nome) {
	
		HelloRequest request = HelloRequest.newBuilder().setNome(nome).build();
		HelloResponse response = helloService.digaOla(request);
		
		return response.getMensagem() + " , Quantidade : " + response.getQuantidadeDeChamadas();
	}
	
}
