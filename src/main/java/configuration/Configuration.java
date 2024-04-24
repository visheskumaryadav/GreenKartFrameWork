package configuration;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;

public class Configuration {

    //This is filepath of json configuration file
    final String filePath=System.getProperty("user.dir")+"/src/main/java/Configuration/configuration.json";
    private JSONObject jsonConfigData;
    public Configuration(){
     configurationSetUp(filePath);
    }
    private void configurationSetUp(String filePath)  {

        try(FileReader file=new FileReader(filePath)){
            //  JSONTokener to parse the JSON file
            JSONTokener token=new JSONTokener(file);
            //parse the json file into jsonObject
            jsonConfigData=new JSONObject(token);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public JSONObject getJsonConfigData(){
        return jsonConfigData;
    }

}
