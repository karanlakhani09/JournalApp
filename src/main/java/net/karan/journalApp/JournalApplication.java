package net.karan.journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {
    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    //spring ko batana padega ptm (interface) uska implementation mtm h
    @Bean
    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
        //(mdf)helps in connection b/w database , iske instance se kaam krenge
        //mdf ek interface h jisko (simple mongoclientdatabasefactory.class impliment karti h

        return new MongoTransactionManager(dbFactory);
    }

}
//PlatformTransactionManager(interface)
//MongoTransactionManager(implementation of interface
