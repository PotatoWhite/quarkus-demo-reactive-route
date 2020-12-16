package me.potato.demo;

import io.quarkus.vertx.web.*;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@RouteBase(path = "demo")
@ApplicationScoped
public class MyDeclarativeRoutes {

  @Route(methods = HttpMethod.GET)
  void hello(RoutingContext routingContext) throws UnsupportedOperationException {
    routingContext.response()
                  .end("Hello");
  }

  @Route(path = "/world")
  String helloWorld() {
    return "HelloWorld";
  }

  @Route(path = "/greetings", methods = HttpMethod.GET)
  void greetings(RoutingExchange exchange) {
    exchange.ok("Hello "+exchange.getParam("name")
                                 .orElse("world"));

  }

  @Route(path = "/greetings2", methods = HttpMethod.GET)
  void greetings2(RoutingExchange exchange, @Param Optional<String> name) {
    exchange.ok("Hello2 "+name.orElse("world"));
  }

  @Route(path = "/post", methods = HttpMethod.POST)
  @Blocking
  public void blocking(RoutingContext routingContext) {
  }

  @Route(path = "/first")
  @Route(path = "/second")
  public String route(RoutingContext context) {
    return "first and second";
  }

  @Route(path = "/person", produces = "text/html", order = 2, methods = HttpMethod.GET)
  public String person() {
    return "person";
  }

  @Route(path = "/account/:id", methods = HttpMethod.GET, order = 2)
  String getAccount(RoutingExchange exchange, @Param("id") String id) {
    return id;

  }

  @Route(path = "/account/me", methods = HttpMethod.GET, order = 1)
  String getCurrentAccount(RoutingContext context) {
    return "i am";

  }


  @Route(path = "/person", methods = HttpMethod.POST)
  @Route(path = "/person/:id", methods = HttpMethod.POST, order = 1)
  @Produces(MediaType.APPLICATION_JSON)
  Person echoPerson(@Body Person person, @Param Optional<Integer> id) {
    return id.map(pk -> new Person(pk, null, null))
             .orElse(person);
  }

  // counldn't be work
  @Route(type = Route.HandlerType.FAILURE)
  void unsupported(UnsupportedOperationException e, HttpServerResponse response) {
    response.setStatusCode(Response.Status.NOT_IMPLEMENTED.getStatusCode())
            .end(e.getMessage());
  }



  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Person {
    private Integer id;

    @NotNull
    private String  name;
    private String  email;
  }
}
