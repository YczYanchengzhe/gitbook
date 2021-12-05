package demo;

import org.apache.pulsar.client.api.*;

/**
 * @author chengzhe yan
 * @description
 * @date 2021/12/5 11:12 上午
 */
public class Demo {

	@SuppressWarnings("all")
	public static void main(String[] args) throws PulsarClientException {
		PulsarClient client = PulsarClient.builder()
				.serviceUrl("pulsar://localhost:6650")
				.build();

		new Thread(()->{
			while (true) {
				try {
					Thread.sleep(1000);
					send(client);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(()->{
			try {
				recv(client);
			} catch (PulsarClientException e) {
				e.printStackTrace();
			}
		}).start();

	}

	@SuppressWarnings("rawtypes")
	private static void recv(PulsarClient client) throws PulsarClientException {
		Consumer consumer = client.newConsumer()
				.topic("my-topic")
				.subscriptionName("my-subscription")
				.subscribe();
		while (true) {
			// Wait for a message
			Message msg = consumer.receive();

			try {
				// Do something with the message
				System.out.println("Message received: " + new String(msg.getData()));

				// Acknowledge the message so that it can be deleted by the message broker
				consumer.acknowledge(msg);
			} catch (Exception e) {
				// Message failed to process, redeliver later
				consumer.negativeAcknowledge(msg);
			}
		}
	}

	private static void send(PulsarClient client) throws PulsarClientException {
		Producer<byte[]> producer = client.newProducer()
				.topic("my-topic")
				.create();

		// You can then send messages to the broker and topic you specified:
		producer.send("My message 123".getBytes());
		producer.close();
	}
}
