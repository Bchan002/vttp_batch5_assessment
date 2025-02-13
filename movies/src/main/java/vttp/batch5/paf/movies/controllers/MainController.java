package vttp.batch5.paf.movies.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonArray;
import vttp.batch5.paf.movies.services.MovieService;

@Controller
@RequestMapping("/api")
public class MainController {

  @Autowired
  private MovieService movieSvc;

  // TODO: Task 3
   @GetMapping("/summary")
   @ResponseBody
    public ResponseEntity<String> getList(@RequestParam MultiValueMap<String,String> form){
      
      int getCount = Integer.parseInt(form.getFirst("count"));
      JsonArray getList = movieSvc.getProlificDirectors(getCount);

      return ResponseEntity.ok().body(getList.toArray().toString());
      
    }

  
  // TODO: Task 4


}
