package com.kingofthehill;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

/**
 * Created by patrik on 2016-06-18.
 */
public class KingofthehillApplication extends Application<KingofthehillConfiguration> {

    public static void main(String[] args) throws Exception {
        new KingofthehillApplication().run(args);
    }

    @Override
    public String getName() {
        return "king-of-the-hill";
    }

    @Override
    public void initialize(Bootstrap<KingofthehillConfiguration> bootstrap) {
        bootstrap.addBundle(new DBIExceptionsBundle());
    }

    public void run(KingofthehillConfiguration configuration, Environment environment) throws Exception {

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "king");
        final KingDAO dao = jdbi.onDemand(KingDAO.class);
        environment.jersey().register(new HelloWorldResource(dao));
    }
}
