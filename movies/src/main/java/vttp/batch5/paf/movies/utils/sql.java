package vttp.batch5.paf.movies.utils;

public class sql {
    
    public static final String insertSql = """
            INSERT into imdb (imbd_id,vote_average,vote_count,release_date,revenue,budget,runtime) values (?,?,?,?,?,?,?)
            """;
}
