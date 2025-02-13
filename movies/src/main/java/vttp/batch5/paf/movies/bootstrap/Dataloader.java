package vttp.batch5.paf.movies.bootstrap;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.paf.movies.models.Imdb;
import vttp.batch5.paf.movies.services.MovieService;
@Component
public class Dataloader implements CommandLineRunner {

    @Autowired
    private MovieService movieSvc;


  private final Logger logger = Logger.getLogger(Dataloader.class.getName());
  //TODO: Task 2

  @Override
  public void run(String ... main){

      InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/movies_post_2010.zip");
      String fileZip = "/Users/benjaminchan/Desktop/paf_b5_assessment_template/data/movies_post_2010.zip";
     try{

    

      // // Handle null case if the file doesn't exist
      // if (inputStream == null) {
      //     throw new FileNotFoundException("Resource not found: static/products.json");
      // }
      ZipInputStream zipIn = new ZipInputStream(new FileInputStream(fileZip));
      ZipEntry entry = zipIn.getNextEntry();

      List<Document> docList = new ArrayList<>();
      while (entry != null) {
        // Print the name of each file in zip
        System.out.println("Found file: " + entry.getName());

        if (entry.getName().endsWith(".json")) {
            //List of Document 
           

            StringBuilder jsonContent = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(zipIn))) {
                String line;
                while ((line = br.readLine()) != null) {
                    //Read json String line by line
                    line = line.replaceAll("\r?\n?\false\0", "");
                    Reader json = new StringReader(line);
                    JsonReader reader = Json.createReader(json);
                    JsonObject jsonObj = reader.readObject();
                    Document doc = Document.parse(jsonObj.toString());

                    //Check the date 
                    int date = Integer.parseInt(doc.getString("release_date").substring(0,4));
                    //System.out.println("your date is " + date);

                    if(date>=2018){ 

                      /*
                       *  
                       */
                       
                        //Check the errors 
                        Document cleanedDoc = new Document();
    
                        // Impute missing values with defaults based on type
                        cleanedDoc.put("title", doc.get("title", ""));
                        cleanedDoc.put("vote_average", doc.getInteger("vote_average", 0));
                        cleanedDoc.put("vote_count", doc.getInteger("vote_count", 0));
                        cleanedDoc.put("status", doc.get("status", ""));
                        cleanedDoc.put("release_date", doc.get("release_date", ""));
                        cleanedDoc.put("revenue", doc.get("revenue", 0));
                        cleanedDoc.put("runtime", doc.getInteger("runtime", 0));
                        cleanedDoc.put("budget", doc.get("budget", 0));
                        cleanedDoc.put("imbd_id", doc.get("imdb_id", ""));
                        cleanedDoc.put("rating", doc.get("rating", 0.0));
                        cleanedDoc.put("genre", doc.get("genre", ""));
                        cleanedDoc.put("director", doc.get("director", ""));
                        cleanedDoc.put("overview", doc.get("overview", ""));
                        cleanedDoc.put("tagline", doc.get("tagline", ""));
                        cleanedDoc.put("genres", doc.get("genres", ""));
                        cleanedDoc.put("imdb_id",doc.getString("imdb_id"));

                        //Add inside the list of documents 
                        docList.add(cleanedDoc);

                    }
                }
                br.close();
                
            } 

            // 
            //System.out.println("JSON content: " + jsonContent.toString());
           
            // Print specific JSON values to verify parsin
            
        }
        System.out.println("size of doc " + docList.size());

        //Send to movieSvc for batch insert 
        movieSvc.addFilteredJsonDoc(docList);

    }
    } catch (Exception e){
          e.printStackTrace();
    }
  }
 

}
