package se.olapetersson.examples.future;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.logging.Logger;

@Path("future")
public class CompletableFutureEndpoint {

    @GET
    @Path("/sync")
    public String syncGet() {
        String foo = getFoo();
        return foo;
    }

    @GET
    @Path("/async")
    public String asyncGet() {

        return "not implemented yet :(";
    }

















    private String getFoo() {
        /**
         * @author The Summer Intern
         *
         * @not Ola
         */
        sleepThread(500);
        return "I'm Foo";
    }














    private void sleepThread(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
