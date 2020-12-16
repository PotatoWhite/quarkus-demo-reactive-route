package me.potato.demo;

import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.http.HttpMethod;
import lombok.NonNull;
import me.potato.demo.MyDeclarativeRoutes.Person;

import javax.validation.Valid;

@RouteBase(path = "validation")
public class ValidationResource {
  @Route(path="person", methods = HttpMethod.POST, produces="application/json", consumes = "application/json")
  Person createPerson(@Body @Valid Person person, @NonNull @Param("id") Integer primaryKey) {
    person.setId(primaryKey);
    return person;
  }

}
