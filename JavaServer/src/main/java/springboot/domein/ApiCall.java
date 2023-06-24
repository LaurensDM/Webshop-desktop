package springboot.domein;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import springboot.controller.UserController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiCall {
    private static final Logger logger = LogManager.getLogger(ApiCall.class);

    /**
     * @param urlString without the base url
     * @return JSONObject
     * @throws IOException
     */
    public static JSONObject get(String urlString) throws IOException {
        logger.info(String.format("INFO -- ApiCall.get(%s)%n", urlString));
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        int responseCode = con.getResponseCode();
        logger.info(String.format("\tResponse Code : " + responseCode));
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            String inline = "";
            Scanner scanner = new Scanner(con.getInputStream());
            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }
            scanner.close();

            //Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();
            JSONObject jsonData = null;
            try {
                jsonData = (JSONObject) parse.parse(inline);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            con.disconnect();
            logger.info(String.format("\tResponse JSONObject: %s%n", jsonData));
            return jsonData;
        }
    }

}
