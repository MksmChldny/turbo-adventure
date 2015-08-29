package de.atomfrede.github.karaoke.server.resource;

import com.mongodb.DB;
import de.atomfrede.github.karaoke.server.entity.Pong;
import de.atomfrede.github.karaoke.server.mongo.JongoManaged;
import de.atomfrede.github.karaoke.server.mongo.MongoHealthCheck;
import de.atomfrede.github.karaoke.server.resource.PingResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.jongo.Jongo;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JMockit.class)
public class PingResourceTest {

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new PingResource()).build();
    @Mocked
    JongoManaged jongoManaged;
    @Mocked
    MongoHealthCheck mongoHealthCheck;
    @Mocked
    DB db;
    @Mocked
    Jongo jongo;

    @Test
    public void shouldReturnNewPong() {

        Pong p = resources.client().target("/ping/pong").request().get(Pong.class);

        assertThat(p, notNullValue());

    }
}
