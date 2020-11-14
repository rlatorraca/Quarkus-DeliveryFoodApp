package ca.com.rlsp.delivery.grpc;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import ca.com.rlsp.proto.HelloRequest;
import ca.com.rlsp.proto.HelloResponse;
import ca.com.rlsp.proto.HelloServiceGrpc.HelloServiceBlockingStub;
import ca.com.rlsp.proto.MutinyHelloServiceGrpc.MutinyHelloServiceStub;
import io.quarkus.grpc.runtime.annotations.GrpcService;
import io.smallrye.mutiny.Uni;

@Path("/ola")
public class HelloService {

    @Inject
    @GrpcService("hello-service")
    HelloServiceBlockingStub olaService;

    @Inject
    @GrpcService("hello-service")
    MutinyHelloServiceStub helloServiceMutiny;

    @GET
    @Path("block/{nome}")
    public String getBlock(@PathParam("nome") String nome) {
        HelloRequest request = HelloRequest.newBuilder().setNome(nome).build();
		HelloResponse response = olaService.digaOla(request);
        return response.getMensagem()+", Quantidade: "+response.getQuantidadeDeChamadas();
    }

   
	@GET
    @Path("reativo/{nome}")
    public Uni<String> getReativo(@PathParam("nome") String nome) {
    	HelloRequest request = HelloRequest.newBuilder().setNome(nome).build();
        Uni<HelloResponse> response = helloServiceMutiny.digaOla(request);
        return response.onItem().transform(i -> i.getMensagem()+", Quantidade: "+i.getQuantidadeDeChamadas());
    }

}
