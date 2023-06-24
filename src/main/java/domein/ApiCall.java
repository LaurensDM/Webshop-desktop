package domein;

import main.Main;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;
import com.google.gson.Gson;
import resources.StaticLoader;

import java.net.http.HttpClient;


public class ApiCall {
    private static final String API_URL = Objects.equals(
            StaticLoader.appProps.getProperty("application.env", "development"), "production"
    ) ? StaticLoader.appProps.getProperty("server.production_api") : StaticLoader.appProps.getProperty("server.local_api");

    /**
     * @param urlString without the base url (e.g. "/user/all")
     * @param token can be null if token is not needed
     * @return JSONObject that contains the response or {"error": "error message"}
     * @throws IOException
     */
    public static JSONObject get(String urlString, String token) throws IOException {
        System.out.printf("INFO -- ApiCall.get(%s, %s)%n", API_URL+urlString, token);
        URL url = new URL(API_URL + urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        if (token != null) {
            con.setDoInput(true);
            con.setRequestProperty("Authorization", "Bearer " + token);
        }
        int responseCode = con.getResponseCode();
        System.out.println("\tResponse Code : " + responseCode);
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
            System.out.printf("\tResponse JSONObject: %s%n", jsonData);
            return jsonData;
        }
    }

    /**
     * @param urlString without the base url (e.g. "/user")
     * @param token can be null if token is not needed (for post it usually is)
     * @return JSONObject that contains the response or {"error": "error message"}
     * @throws IOException
     * @throws InterruptedException
     */
    public static JSONObject post(String urlString, Object object, String token) throws IOException, InterruptedException {
        String postString;
        if (object instanceof JSONObject) {
            postString = ((JSONObject) object).toJSONString();
        } else {
            postString = new Gson().toJson(object);
        }
        System.out.printf("INFO -- ApiCall.post(%s, %s)%n", API_URL+urlString, postString);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        if (token != null) {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + urlString))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofString(postString))
                    .build();
        } else {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + urlString))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(postString))
                    .build();
        }

//        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // parse response to a JSONObject
        JSONParser parse = new JSONParser();
        JSONObject data_obj = null;
        try {
            data_obj = (JSONObject) parse.parse(response.body());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return data_obj;
    }

    /**
     * @param urlString without the base url (e.g. "/user")
     * @param token can be null if token is not needed (for post it usually is)
     * @throws IOException
     */
    public static void put(String urlString, Object object, String token) throws IOException {
        System.out.printf("INFO -- ApiCall.put(%s, ", urlString);
        String putString;
        if (object instanceof JSONObject) {
            putString = ((JSONObject) object).toJSONString();
            System.out.printf("\tDetected JSONObject %s)%n", putString);
        } else {
            putString = new Gson().toJson(object);
            System.out.printf("\tDetected JavaObject %s)%n", putString);
        }
        System.out.printf("INFO -- ApiCall.put(%s, %s)%n", urlString, putString);

        System.out.println("put" + urlString + " " + putString);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + urlString))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .PUT(HttpRequest.BodyPublishers.ofString(putString))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        if (response.statusCode() != 200) {
            throw new RuntimeException("HttpResponseCode: " + response.statusCode());
        } else {
            System.out.println("\tResponse Code : " + response.statusCode());
        }
    }

    public static JSONObject login(String subUrl, String email, String password) throws InterruptedException, IOException {
        HttpClient client = HttpClient.newHttpClient();
        System.out.println(API_URL);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + subUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .build();


        // TODO: make this async
//        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // check if response code is 201
        if (response.statusCode() != 201) {
            throw new RuntimeException("HttpResponseCode: " + response.statusCode());
        }

        // parse response to a JSONObject
        JSONParser parse = new JSONParser();
        JSONObject data_obj = null;
        try {
            data_obj = (JSONObject) parse.parse(response.body());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return data_obj;
    }

    public static Boolean pingServer() {
        JSONObject response;
        try {
            response = get("/health/ping", null);
        } catch (IOException e) {
            return false;
        }
        return response.get("wing").equals(true);
    }

    public static boolean waitForServer() {
        System.out.printf("INFO -- ApiCall.waitForServer() -- waiting for server...%n");
        int count = 0;
        while (!pingServer()) {
            count++;
            if (count > 10) {
                System.out.println("\tserver not responding");
                return false;
            }
            try {
                System.out.println("\tpinged server " + count + " times");
                Thread.sleep(4000);
                // wait 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
//                return false;
                Thread.currentThread().interrupt();
            }
        }
        return true;
    }
}
