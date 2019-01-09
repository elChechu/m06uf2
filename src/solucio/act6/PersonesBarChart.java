/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solucio.act6;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author julian
 */
public class PersonesBarChart extends BasicBarChart {

    private String[] categories;
    private double[] data;
    
    public static void main(String[] args) {
        launch(args);   
    }

    @Override
    public String[] getCatData() {
        return this.categories;
    }

    @Override
    public double[] getYData() {
        return this.data;
    } 
    
    static final int MAX = 12;
    static final int MOD = 5;
    
    @Override
    public void calculate() {
        
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("testdb");
        
        MongoCollection<Document> collection = database.getCollection("mitjans");
        
        this.categories = new String[MAX];
        this.data = new double[MAX];
        
        for (int i=0; i<MAX; i++) {
            this.categories[i] = 
                    Integer.toString(i*MOD) + "-" + Integer.toString(i*MOD+MOD-1);
            this.data[i] = 0;
        }
        
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                int edat = ((Number) doc.get("edat")).intValue();
                int rang = edat / MOD;
                if (rang >= 0 && rang < MAX) {
                    this.data[rang]++;
                }
            }
        }
    }
}
