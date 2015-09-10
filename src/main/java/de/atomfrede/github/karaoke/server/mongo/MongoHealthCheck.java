package de.atomfrede.github.karaoke.server.mongo;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MongoHealthCheck extends HealthCheck {

    private MongoClient mongo;

    public MongoHealthCheck(MongoClient mongo) {
        this.mongo = mongo;
    }

    @Overrides
    protected Result check() throws Exception {
        ExecutorService executor = null;

        try {

            executor = Executors.newSingleThreadExecutor();

            Future<String> future = executor.submit(() -> mongo.listDatabaseNames().first());

            String dbName = future.get(250, TimeUnit.MILLISECONDS);

            if (StringUtils.isEmpty(dbName))
                return Result.unhealthy("Can't connect to database.");

            return Result.healthy();
        } catch (Exception e) {
            return Result.unhealthy(e);
        } finally {

            if (executor != null)
                executor.shutdownNow();
        }

    }
}
