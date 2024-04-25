package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class CurrencyConverter {
    private static JSONArray currencies;
    private JSONObject conversionData;

    private final static String API_KEY = "acbee8c7cf00060fb5d8b622";

    public static String sendRequest(String url) {
        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | IllegalArgumentException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    static private void setCurrencies() {
        final String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/codes";
        String jsonString = sendRequest(url);
        if (jsonString == null ) {
            return;
        }
        currencies = new JSONObject(jsonString).getJSONArray("supported_codes");
    }

    static void displayList() {
        System.out.println("Fetching currency symbols...\n");
        setCurrencies();
        int n = currencies.length();
        for (int i = 1; i <= n; i++) {
            JSONArray array = (JSONArray) currencies.get(i - 1);
            System.out.println(i  + ". " + array.get(1) + " (" + array.get(0) + ")");
        }
    }

    static JSONObject getPairedResponse(String baseCode, String targetCode) {
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY +  "/pair/" + baseCode + "/" + targetCode;
        System.out.println("\nFetching data...\n");
        String jsonBody = sendRequest(url);
        if (jsonBody == null) {
            return null;
        }
        JSONObject data = new JSONObject(jsonBody);

        if (data.getString("result").equals("error")) {
            if (data.getString("error-type").equals("unsupported-code")) {
                System.out.println("[INVALID_CURRENCY_CODE] Please ensure the base code or target code is valid.");
            } else {
                System.out.println("[INVALID_ACCESS_KEY] Please ensure your api key is active.");
            }
            return null;
        }
        return data;
    }

    public static void main(String[] args) {
        System.out.println("============================ VALID CODES ARE =====================");
        displayList();
        System.out.println("==================================================================");
        Scanner sc = new Scanner(System.in);
        System.out.print("BASE CURRENCY CODE : ");
        String baseCode = sc.next().toUpperCase();
        System.out.print("TARGET CURRENCY CODE : ");
        String targetCode = sc.next().toUpperCase();

        JSONObject data = getPairedResponse(baseCode, targetCode);
        if (data == null) {
            return;
        }
        double rate = data.getDouble("conversion_rate");
        System.out.print("BASE AMOUNT : ");
        double baseAmount = sc.nextDouble();
        sc.close();

        double targetAmount = baseAmount * rate;

        String formatted = String.format("%.2f %s = %.2f %s", baseAmount, baseCode, targetAmount, targetCode);
//        System.out.println(baseAmount + " " + baseCode + " = " + targetAmount + " " + targetCode);
        System.out.println(formatted);
    }
}
