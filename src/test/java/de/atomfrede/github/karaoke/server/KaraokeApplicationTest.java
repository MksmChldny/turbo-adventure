package de.atomfrede.github.karaoke.server;

import com.codahale.metrics.health.HealthCheckRegistry;
import de.atomfrede.github.karaoke.server.config.KaraokeConfiguration;
import de.atomfrede.github.karaoke.server.mongo.JongoManaged;
import de.atomfrede.github.karaoke.server.mongo.MongoHealthCheck;
import de.atomfrede.github.karaoke.server.resource.PairingResource;
import de.atomfrede.github.karaoke.server.resource.PingResource;
import de.atomfrede.github.karaoke.server.resource.SingerResource;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Environment;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import net.sourceforge.htmlunit.corejs.javascript.tools.shell.Main;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mongeez.Mongeez;

@RunWith(JMockit.class)
public class KaraokeApplicationTest {

    @Mocked
    Environment environment;
    @Mocked
    JerseyEnvironment jersey;
    @Mocked
    LifecycleEnvironment lifecycle;
    @Mocked
    HealthCheckRegistry healthChecks;
    @Mocked
    Mongeez mongeez;

    KaraokeApplication application = new KaraokeApplication();
    KaraokeConfiguration configuration = new KaraokeConfiguration();

    @Before
    public void setup() {

        new NonStrictExpectations() {{

            environment.jersey();
            result = jersey;

            environment.lifecycle();
            result = lifecycle;

            environment.healthChecks();
            result = healthChecks;

        }};
    }

    @Test
    public void shouldAddPingResource() throws Exception {

        application.run(configuration, environment);

        new Verifications() {{

            jersey.register(withInstanceOf(PingResource.class));
        }};
    }

    @Test
    public void shouldAddSingerResource() throws Exception {

        application.run(configuration, environment);

        new Verifications() {{

            jersey.register(withInstanceOf(SingerResource.class));
        }};
    }

    @Test
    public void shouldAddPairingResource() throws Exception {

        application.run(configuration, environment);

        new Verifications() {{

            jersey.register(withInstanceOf(PairingResource.class));
        }};
    }

    @Test
    public void shouldAddMongoHealthCheck() throws Exception {

        application.run(configuration, environment);

        new Verifications() {{

            healthChecks.register("MongoDB", withInstanceOf(MongoHealthCheck.class));
        }};
    }

    @Test
    public void shouldManageMongo() throws Exception {

        application.run(configuration, environment);

        new Verifications() {{

            environment.lifecycle().manage(withInstanceOf(JongoManaged.class));
        }};
    }

    @Test
    public void shouldMigrateMongo() throws Exception {

        application.run(configuration, environment);

        new Verifications() {{

            mongeez.process();
            times = 1;
        }};
    }
}
