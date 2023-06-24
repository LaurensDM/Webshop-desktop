package springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

@SpringBootApplication
public class JavaServerMain {
    private static final Logger logger = LoggerFactory.getLogger(JavaServerMain.class);
    public static final Properties appProps = new Properties();
    private static String port;

    public static void main(String[] args) {
        SpringApplication javaServer = new SpringApplication(JavaServerMain.class);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        String classpath = System.getProperty("java.class.path");
        String resourceName = "application.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        logger.info(String.format("Loading application.properties from %s%n", resourceName));
        try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            appProps.load(resourceStream);
            port = appProps.getProperty("application.port");
        } catch (IOException e) {
            logger.error("Could not load application.properties" + e.getMessage());
            System.exit(1);
        }

        if (port == null) {
            logger.error("Could not find application.port in application.properties " +
                    "\n\t-> configure it and restart the server"
            );
            System.exit(1);
        }
        
        logger.info(String.format("Starting JavaServer on port %s%n", port));


        javaServer.setDefaultProperties(Collections
                .singletonMap("server.port", port));
        javaServer.run(args);
    }
}
