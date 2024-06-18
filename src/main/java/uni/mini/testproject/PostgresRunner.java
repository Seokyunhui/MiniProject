package uni.mini.testproject;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
@Component
public class PostgresRunner implements ApplicationRunner {
    @Autowired
    DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public void run(ApplicationArguments args) {
        try{
            Connection connection =  dataSource.getConnection();
            System.out.println(dataSource.getClass());
            System.out.println(connection.getMetaData().getURL());
            System.out.println(connection.getMetaData().getUserName());

            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS public.user (" +
                    "  username varchar(45) NOT NULL,\n" +
                    "  password varchar(450) NOT NULL,\n" +
                    "  enabled integer NOT NULL DEFAULT '1',\n" +
                    "  PRIMARY KEY (username) ) ";
            statement.executeUpdate(sql);
            jdbcTemplate.execute("INSERT INTO public.user VALUES ('admin', 'admin' ,1) ON CONFLICT DO NOTHING");
        }catch (Exception e){

        }
    }
}
