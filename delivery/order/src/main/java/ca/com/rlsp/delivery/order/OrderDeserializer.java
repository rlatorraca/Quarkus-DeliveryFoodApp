package ca.com.rlsp.delivery.order;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;


public class OrderDeserializer extends ObjectMapperDeserializer<OrderDoneDTO> {

	
	public OrderDeserializer() {
		super(OrderDoneDTO.class);		
	}

	
	

}
