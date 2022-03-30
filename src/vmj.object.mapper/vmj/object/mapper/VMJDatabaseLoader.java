package vmj.object.mapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.io.FileNotFoundException;
import java.io.IOException;

public class VMJDatabaseLoader {
    private String databaseUrl;

    public VMJDatabaseLoader() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("database.properties"));   
            this.databaseUrl = prop.getProperty("vmjdb.datasource.url");
        } catch (FileNotFoundException e) {
            System.out.println("File database.properties not found in module path.");
            System.exit(30);
            //TODO: handle exception
        } catch (IOException e) {
            System.out.println("IO Exception");
            System.exit(30);
        }
    }

    public String getDatabaseUrl() {
        return this.databaseUrl;
    }

}