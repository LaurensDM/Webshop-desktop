package resources;

import javafx.concurrent.Task;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUpload extends Task<Void> {
    private File file;
    private String url;

    public FileUpload(File file, String url) {
        this.file = file;
        this.url = url;
    }
    @Override
    public Void call() throws Exception {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("multipart/form-data");
        RequestBody body = new MultipartBody.Builder().setType(mediaType)
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/*"),
                                file))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "multipart/form-data")
                .build();
        System.out.println(request.toString());
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // The upload was successful
            } else {
                System.out.println(response.code());
                System.err.println(response.body());
                // There was an error
            }
        } catch (IOException e) {
            e.printStackTrace();
            // There was an error sending the request or handling the response
        }

        return null;
    }
}
