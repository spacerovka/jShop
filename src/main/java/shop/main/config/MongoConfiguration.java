package shop.main.config;

import com.mongodb.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;
import java.util.Arrays;


@Configuration
@EnableMongoRepositories(basePackages = {"shop.main.data.mongo"})
public class MongoConfiguration  extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "test";
    }

    public @Bean
    Mongo mongo() throws UnknownHostException {
        ServerAddress serverAddress = new ServerAddress("localhost", 27017);
//        MongoCredential credential = MongoCredential.createMongoCRCredential("onyshenko","test","onyshenko".toCharArray());
//        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(4).socketKeepAlive(true).build();
//        Mongo mongo = new MongoClient(serverAddress, Arrays.asList(credential),options);
        //mongo.setWriteConcern(WriteConcern.SAFE);
        Mongo mongo = new MongoClient(serverAddress);
        return mongo;
    }
    @Bean(name="MongoTemplate")
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "test");
    }

}
