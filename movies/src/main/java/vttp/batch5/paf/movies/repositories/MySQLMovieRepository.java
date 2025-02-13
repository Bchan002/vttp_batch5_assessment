package vttp.batch5.paf.movies.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.paf.movies.models.Imdb;
import vttp.batch5.paf.movies.utils.sql;
@Repository
public class MySQLMovieRepository {

  @Autowired
  private JdbcTemplate template;
  // TODO: Task 2.3
  // You can add any number of parameters and return any type from the method

  // Batch update for my sql 
  /* 
   *  INSERT into imdb (imdb_id,vote_average,vote_count,release_date,revenue,budget,runtime) values (?,?,?,?,?,?,?)
   */
  public void batchInsertMovies(List<Imdb> imdb) {

     //Create a List of Object array that matches the order of the prepared statements
        List<Object[]> params = imdb.stream()
            .map(m -> new Object[]{m.getImdb_id(), m.getVote_average(), m.getVote_count(), m.getRelease_date(),
              m.getRevenue(),m.getBudget(),m.getRuntime()})
            .collect(Collectors.toList());

        int added[] = template.batchUpdate(sql.insertSql, params);
        



  }
  
  // TODO: Task 3


}
