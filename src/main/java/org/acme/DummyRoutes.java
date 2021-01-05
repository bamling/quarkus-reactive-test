package org.acme;

import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
public class DummyRoutes {

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    EventBus eventBus;

    @Route(methods = HttpMethod.GET, path = "/dummy")
    public Uni<String> hello() {
        System.out.println("in routes: " + jsonWebToken.getClaimNames());

        return eventBus.<String>request("world", "Hello World!")
            .onItem().transform(Message::body);
    }

}
