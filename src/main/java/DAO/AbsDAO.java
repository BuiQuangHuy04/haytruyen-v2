package DAO;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public abstract class AbsDAO {

    static MongoDatabase db;

    //ket nôi den database "Manga"
    MongoDatabase getDB() {
        if (db == null) {
            ConnectionString connectionString = new ConnectionString("mongodb+srv://root:root@cluster0.71wmt.mongodb.net/Manga?retryWrites=true&w=majority");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .codecRegistry(fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build())))
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            //ket noi database "Manga"
            db = mongoClient.getDatabase("Manga");
        }
        return db;
    }
}