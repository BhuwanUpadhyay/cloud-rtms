package io.github.bhuwanupadhyay.rtms.notification;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import java.net.ServerSocket;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Provider("app")
@PactBroker(
    host = "localhost",
    port = "9292",
    consumers = {"web-ui", "flutter-app"})
class PactVerificationTest {

  private static int port;

  @SneakyThrows
  @BeforeAll
  static void start() {
    port = new ServerSocket(0).getLocalPort();
    new SpringApplicationBuilder(SpringBoot.class)
        .profiles("test", "pact")
        .properties("server.port=" + port)
        .build()
        .run();
  }

  @BeforeEach
  void before(PactVerificationContext context) {
    context.setTarget(new HttpTestTarget("localhost", port, "/"));
  }

  @State({"rtms actions"})
  void toDefaultState() {}

  @TestTemplate
  @ExtendWith(PactVerificationInvocationContextProvider.class)
  void pactVerificationTestTemplate(PactVerificationContext context) {
    context.verifyInteraction();
  }
}
