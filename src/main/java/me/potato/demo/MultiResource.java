package me.potato.demo;

import io.quarkus.vertx.web.ReactiveRoutes;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Multi;
import io.vertx.ext.web.RoutingContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.potato.demo.MyDeclarativeRoutes.Person;

@RouteBase(path = "multi", produces = "application/json")
public class MultiResource {
  @Route(path = "hello")
  Multi<String> hellos(RoutingContext context) {
    return Multi.createFrom()
                .items("hello", "world", "!");
  }

  @Route(path = "/people")
  Multi<Person> people(RoutingContext context) {
    return Multi.createFrom()
                .items(new Person(1, "12", "123"),
                       new Person(2, "23", "234"));
  }

  @Route(path = "/stream/people")
  Multi<Person> peopleStream(RoutingContext context) {
    return ReactiveRoutes.asJsonArray(Multi.createFrom()
                                           .items(new Person(1, "12", "123"),
                                                  new Person(2, "23", "234")));
  }

  @Route(path = "/sse/people")
  Multi<Person> peopleSSE(RoutingContext context) {
    return ReactiveRoutes.asEventStream(Multi.createFrom()
                                             .items(new Person(1, "12", "123"),
                                                    new Person(2, "23", "234")));
  }

  @Route(path = "/sse2/people")
  Multi<PersonEvent> peopleSSE2(RoutingContext context) {
    return ReactiveRoutes.asEventStream(Multi.createFrom()
                                             .items(new PersonEvent(1, "12", "123"),
                                                    new PersonEvent(2, "23", "234")));
  }


  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  static class PersonEvent implements ReactiveRoutes.ServerSentEvent<Person> {
    private int id;
    private String name;
    private String email;


    @Override
    public String event() {
      return PersonEvent.class.getSimpleName();
    }

    @Override
    public Person data() {
      return new Person(id, name, email);
    }

    @Override
    public long id() {
      return id;
    }
  }
}
