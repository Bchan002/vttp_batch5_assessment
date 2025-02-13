package vttp.batch5.paf.movies.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.batch5.paf.movies.models.Imdb;
import vttp.batch5.paf.movies.repositories.MongoMovieRepository;
import vttp.batch5.paf.movies.repositories.MySQLMovieRepository;

@Service
public class MovieService {

  @Autowired
  private MySQLMovieRepository mysqlMovieRepo;

  @Autowired 
  private MongoMovieRepository mongoMovieRepo;
  // TODO: Task 2
  
  //Add filtered one to database 
  public void addFilteredJsonDoc(List<Document> doc){

    //Add to mySQL repo 
    List<Imdb> imdbList = new ArrayList<>();
    doc.stream()
      .map(a-> {
          Imdb imdb = new Imdb();
          imdb.setImdb_id(a.getString("imdb_id"));
          imdb.setVote_average(a.getInteger("vote_average"));

          //Convert to date 
          String date = a.getString("release_date");
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          Date date2;
          try {
            date2 = sdf.parse(date);
            imdb.setRelease_date(date2);
            //imdb.setRevenue(a.getLong("revenue"));
            imdb.setBudget(a.getInteger("budget"));
            imdb.setVote_count(a.getInteger("vote_count"));
            imdb.setRuntime(a.getInteger("runtime"));

          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        

          return imdb;
      })
      .forEach(b-> imdbList.add(b));

      //Batch insert to mysql
        int batchSize = 25;
        for(int i=0; i<imdbList.size();i+=batchSize){
            int endIndex = Math.min(i+ batchSize, imdbList.size());
            List<Imdb> imdbList2 = imdbList.subList(i,endIndex);

            //mysqlMovieRepo.batchInsertMovies(imdbList2);
      
        }

        //Batch insert to mongoDB 
       List<Document> doc2 = new ArrayList<>();
       doc.stream()
        .map(a-> {
            Document doc3 = new Document()
                .append("_id",a.getString("imdb_id"))
                .append("title",a.getString("title"))
                .append("directors",a.getString("director"))
                .append("overview",a.getString("overview"))
                .append("tagline",a.getString("tagline"))
                .append("genres",a.getString("generes"))
                .append("imdb_rating",a.getString("imdb_rating"))
                .append("imdb_votes",a.getString("imdb_votes"));

            return doc3;
     
         })
         .forEach(b-> doc2.add(b));

        
         int batchSize2 = 25;
         for(int i=0; i<doc2.size();i+=batchSize){
             int endIndex = Math.min(i+ batchSize2, doc2.size());
             List<Document> imdbList2 = doc2.subList(i,endIndex);
 
             mongoMovieRepo.batchInsertMovies(imdbList2);
       
         }






}         





  // TODO: Task 3
  // You may change the signature of this method by passing any number of parameters
  // and returning any type
  public JsonArray getProlificDirectors(int getCount) {

      List<Document> document = mongoMovieRepo.getAllDirector();

      //Return JsonArray 
      JsonArrayBuilder array = Json.createArrayBuilder();

      document.stream()
        .map(a-> {
            JsonObject object = Json.createObjectBuilder()
              .add("Director", a.getString("director"))
              .add("titles",a.getString("title"))
              .add("revenue",a.getInteger("revenue"))
              .add("budget",a.getInteger("budget"))
              .build();

            return object;
        }
        )
        .forEach(b-> array.add(b));

      JsonArray array2 = array.build();

      return array2;

  }


  // TODO: Task 4
  // You may change the signature of this method by passing any number of parameters
  // and returning any type
  public void generatePDFReport() {

  }

}
