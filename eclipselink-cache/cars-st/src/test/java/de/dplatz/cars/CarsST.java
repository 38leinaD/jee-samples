package de.dplatz.cars;

import static org.junit.Assert.assertThat;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

/**
 * HelloIT
 */
public class CarsST {

    WebTarget server1;
    WebTarget server2;
    JsonObject createdCar;

    @Before
    public void init() {
        server1 = ClientBuilder.newClient().target("http://localhost/cars");
        server1 = ClientBuilder.newClient().target("http://localhost:81/cars");
    }

    @Test
    public void testCacheCoordination() {
        createdCar = createCar(server1, "tesla");
        // Get object by id to populate cache on server 1
        queryCar(server1, createdCar.getInt("id"));
        // Get object by id to populate cache on server 2
        queryCar(server2, createdCar.getInt("id"));
        // Update model of car with id=1 on server 1
        updateCar(server1, createdCar.getInt("id"), "tesla-x");

        // Get object from server 1 again
        JsonObject carOnServer1 = queryCar(server1, createdCar.getInt("id"));
        // As the object was updated here before, if cached, the cache should return the correct object
        assertThat(carOnServer1.getString("model"), CoreMatchers.is("tesla-x"));

        // Get object from server 2 again
        JsonObject carOnServer2 = queryCar(server1, createdCar.getInt("id"));
        // ?
        assertThat(carOnServer2.getString("model"), CoreMatchers.is("tesla-x"));
    }

    JsonObject createCar(WebTarget tut, String model) {
        JsonObject car = Json.createObjectBuilder()
            .add("model", model)
            .build();

        Response response = server1.path("resources/cars").request(MediaType.APPLICATION_JSON).post(Entity.entity(car, MediaType.APPLICATION_JSON));
    
        assertThat(response.getStatus(), CoreMatchers.is(200));
        return response.readEntity(JsonObject.class);
    }

    JsonObject queryCar(WebTarget tut, int id) {
        Response response = server1.path("resources/cars/" + id).request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), CoreMatchers.is(200));
        return response.readEntity(JsonObject.class);
    }

    void updateCar(WebTarget tut, int id, String model) {
        JsonObject car = Json.createObjectBuilder()
            .add("id", id)
            .add("model", model)
            .build();

        Response response = server1.path("resources/cars").request().put(Entity.entity(car, MediaType.APPLICATION_JSON));
        assertThat(response.getStatus(), CoreMatchers.is(204));
    }
}
