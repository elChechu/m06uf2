package solucio.act6;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * 
 * db.persones.updateMany({}, { $inc: { edat: NumberInt(0) } })
 *
 * @author julian
 */
public class PersonesDAO {
    
    public String createPersona(Persona p) {
        
        Document doc = new Document().append("nom", p.getNom()).append("edat", p.getEdat());
        getPersonaCollection().insertOne(doc);
        return doc.getObjectId("_id").toString();
    }

    public Persona findPersona(String idStr) {

        ObjectId objId = null;
        try {
            objId = new ObjectId(idStr);
        } catch (IllegalArgumentException e) {
            // this means the string is not fine
            return null;
        }
        
        Bson bson = Filters.eq("_id", objId);
        Document doc = getPersonaCollection().find(bson).first();        
        return buildPersona(doc);
    }
    
    public boolean addMitja(String idStr, String mitja) {
        return changeMitja(idStr, mitja, true);
    }
    
    public boolean removeMitja(String idStr, String mitja) {
        return changeMitja(idStr, mitja, false);
    }
    
    public boolean changeMitja(String idStr, String mitja, boolean add) {
        
        ObjectId objId = null;
        try {
            objId = new ObjectId(idStr);
        } catch (IllegalArgumentException e) {
            // this means the string is not fine
            return false;
        }
        
        Bson bson = Filters.eq("_id", objId);
        UpdateResult ur = getPersonaCollection().updateOne(bson, 
                add? Updates.push("mitjans", mitja) : Updates.pull("mitjans", mitja));
        return ur.getModifiedCount() > 0;
    }
    
    public List<Persona> findAllPersones() {
        
        List<Persona> result = new ArrayList<>();
        
        try (MongoCursor<Document> cursor = getPersonaCollection().find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();   
                result.add(buildPersona(doc));
            }
        }
        
        return result;
    }
    
    private Persona buildPersona(Document doc) {
        
        String nom = doc.getString("nom");
        //int edat = (int) Double.parseDouble(doc.get("edat").toString());
        int edat = doc.get("edat", Number.class).intValue();
        //int edat = doc.getInteger("edat");
        
        String id = null;
        if (doc.containsKey("_id")) {
            id = doc.getObjectId("_id").toString();
        }
        
        List<String> mitjans = (List<String>) doc.get("mitjans");
        
        return new Persona(id, nom, edat, mitjans);
    }
    
    // UTILS
    
    private MongoClient mongoClient;
    private MongoDatabase database;
    MongoCollection<Document> collection;
    
    private MongoCollection<Document> getPersonaCollection() {
        
        if (this.collection == null) {
            this.mongoClient = MongoClients.create("mongodb://localhost:27017");
            this.database = this.mongoClient.getDatabase("testdb");
            this.collection = this.database.getCollection("persones");
        }
    
        return this.collection;
    }
}
