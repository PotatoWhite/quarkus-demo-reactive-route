package me.potato.demo;

import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import me.potato.demo.MyDeclarativeRoutes.Person;

@RouteBase(path = "uni")
public class UniResource {


  @Route(path = "hello")
  Uni<String> hello(RoutingContext context) {
    return Uni.createFrom()
              .item("Hello World");
  }

  @Route(path = "person")
  Uni<Person> getPerson(RoutingContext context) {
    return Uni.createFrom()
              .item(() -> new Person(1, "potato", "test@test.com"));
  }

  @Route(path = "/mail")
  Uni<Void> sendEmail(RoutingContext context) {
    return Uni.createFrom()
              .nullItem();
  }

}
