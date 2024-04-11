package com.example.ticktacktoe;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class CallApi extends AsyncTask<String, Void, String> {

    private APICallback callback;

    public CallApi(APICallback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... messages) {
        String result = null;
        String apiKey = "";

        try {
            URL url = new URL("https://api.openai.com/v1/chat/completions");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            JSONObject message2 = new JSONObject();
            message2.put("role", "user");
            message2.put("content", messages[0]);

            JSONArray messagesArray = new JSONArray();
            messagesArray.put(message2);

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-3.5-turbo");
            requestBody.put("messages", messagesArray);

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(requestBody.toString());
            outputStream.flush();
            outputStream.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            if (choices.length() > 0) {
                JSONObject messageObject = new JSONObject(choices.getJSONObject(0).getString("message"));
                result = messageObject.getString("content");
                return result;
            } else {
                result = "No choices in response.";
                return result;
            }

        } catch (Exception e) {
            return "Error in API key";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(callback != null) {
            callback.onResult(result);
        }
    }

    interface APICallback {
        void onResult(String result);
    }
}
