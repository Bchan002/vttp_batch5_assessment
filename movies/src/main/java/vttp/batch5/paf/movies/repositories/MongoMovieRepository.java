package vttp.batch5.paf.movies.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.util.*;

@Repository
public class MongoMovieRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

 // TODO: Task 2.3
 // You can add any number of parameters and return any type from the method
 // You can throw any checked exceptions from the method
 // Write the native Mongo query you implement in the method in the comments
 //
 //    native MongoDB query here
 //    
 //    db.imdb.insert({})
 public void batchInsertMovies(List<Document> batch) {

    Collection<Document> insertedDocs = mongoTemplate.insert(batch, "imdb");



 }

 // TODO: Task 2.4
 // You can add any number of parameters and return any type from the method
 // You can throw any checked exceptions from the method
 // Write the native Mongo query you implement in the method in the comments
 //
 //    native MongoDB query here
 //
 public void logError() {

 }

 // TODO: Task 3
 // Write the native Mongo query you implement in the method in the comments
 //
 //    native MongoDB query here
        /*
         * db.imdb2.aggregate([
    {
        $project: {
           director: "$director",
           titles: "$titles", 
           revenue: "$revenue",
           budget: "$budget", 
           profitLoss: {
               $subtract: ["$revenue","$budget"]
           }
           
        }
    },
    
    
])  
         */

 // 
    public List<Document> getAllDirector(){
         
        //Projection Operation 
        ProjectionOperation projectOps = Aggregation.project("director", "titles","revenue","budget");

        Aggregation pipeline = Aggregation.newAggregation(projectOps);

        //Execute the pipeline 
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "imdb", Document.class);

        //Get List<document> 
        List<Document> resultsInDocList = results.getMappedResults();

        return resultsInDocList;
            
	       
    }


}
