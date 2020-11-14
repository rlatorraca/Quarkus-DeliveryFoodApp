package ca.com.rlsp.delivery.grpc;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Singleton;

import ca.com.rlsp.proto.HelloRequest;
import ca.com.rlsp.proto.HelloResponse;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;

/** Se quiser trabalhar sem o Mutiny */

//@Singleton
//public class MyHelloService extends ca.com.rlsp.proto.HelloServiceGrpc.HelloServiceImplBase {
//
//	AtomicInteger inteiro = new AtomicInteger();
//
//	public void digaOla(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
//		String nome = request.getNome();
//
//		responseObserver.onNext(HelloResponse.newBuilder().setMensagem("Ola " + nome)
//				.setQuantidadeDeChamadas(inteiro.getAndIncrement()).build());
//		responseObserver.onCompleted();
//	}
//
//}


/**Trabalhando com Mutiny */

@Singleton
public class MyHelloService extends ca.com.rlsp.proto.MutinyHelloServiceGrpc.HelloServiceImplBase {

    AtomicInteger inteiro = new AtomicInteger();
        @Override
        public Uni<HelloResponse> digaOla(HelloRequest request) {
        	HelloResponse response = HelloResponse.newBuilder()
                    .setMensagem("Hello-2 "+request.getNome())
                    .setQuantidadeDeChamadas(inteiro.getAndIncrement()).build();
			return Uni.createFrom().item(response);
        }
}
