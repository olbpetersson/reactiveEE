import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
 
class OverloadLocalhostSync extends Simulation {
 
    val theHttpProtocolBuilder = http
        .baseURL("http://localhost:8080/reactiveEE")
 
    val theScenarioBuilder = scenario("The ASYNC scenario")
        .exec(
            http("asyncRequest")
                .get("/api/future/sync"))
 
    setUp(
        theScenarioBuilder.inject(atOnceUsers(20))
    ).protocols(theHttpProtocolBuilder)
}