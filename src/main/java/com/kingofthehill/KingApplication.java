package com.kingofthehill;

import com.kingofthehill.repository.Repository;
import com.kingofthehill.repository.dao.KingDAO;
import com.kingofthehill.resource.KingResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Created by patrik on 2016-06-18.
 */
public class KingApplication extends Application<KingoConfiguration> {

    public static void main(String[] args) throws Exception {
        new KingApplication().run(args);
    }

    @Override
    public String getName() {
        return "king-of-the-hill";
    }

    @Override
    public void initialize(Bootstrap<KingoConfiguration> bootstrap) {
        bootstrap.addBundle(new DBIExceptionsBundle());
    }

    public void run(KingoConfiguration configuration, Environment environment) throws Exception {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "king");
        final KingDAO dao = jdbi.onDemand(KingDAO.class);
        final Repository repository = new Repository(dao);

        environment.jersey().register(new KingResource(repository));
    }
}
