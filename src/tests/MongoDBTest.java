package tests;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Map.Entry;
import org.bson.Document;
import org.bson.types.ObjectId;
import persones.Persona;

/**
 *
 * @author julian
 */
public class MongoDBTest {
    
    public static void main(String[] args) {
        
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("testdb");
        
        MongoCollection<Document> collection = database.getCollection("persones");
        
        Document doc = collection.find().first();
        if (doc == null) {
            System.out.println("no rows found");
            return;
        }
        
        for (Entry<String, Object> entry: doc.entrySet()) {
            System.out.println(
                    entry.getKey() + " = " + entry.getValue());
        }
        
        String nom = (String) doc.get("nom");
        int edat = ((Number) doc.get("edat")).intValue();
        ObjectId id = doc.getObjectId("_id");
        String idStr = id.toString();
        
        Persona p = new Persona(idStr, nom, edat, null);
        System.out.println(p);
    }
}
