package org.acme;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class DummyService {

    @Inject
    JsonWebToken jsonWebToken;

    @Blocking // jsonWebToken.getClaimNames() currently blocks the Vertx thread/worker, possible bug?
    @ConsumeEvent("world")
    public Uni<String> world(final String s) {
        System.out.println("in test: " + jsonWebToken.getClaimNames());

        return Uni.createFrom().item(s);
    }
}
