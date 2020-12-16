package me.potato.demo;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.event.Observes;

public class Misc {
  public void init(@Observes Router router) {
    router.get("/demo/world")
          .handler(rc -> rc.response()
                           .end("Hello from Observer"));
  }

  @RouteFilter(100)
  public void someFilter(RoutingContext context) {
    context.response()
           .putHeader("X-Header", "intercept");
    context.next();
  }
}
