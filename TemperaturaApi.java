import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TemperaturaApi {
    
    public static void main(String[] args) {
        
        // Substitua pela sua chave de API
        String chaveApi = "3c81d4265b989ef7b4dbe86b272b9670";
        
        String cidade = "São Paulo";
        
        String cidadeCodificada = URLEncoder.encode(cidade, StandardCharsets.UTF_8);
        
        String lang = "pt_br";
        
        String link = "https://api.openweathermap.org/data/2.5/weather?q=" + cidadeCodificada + "&appid=" + chaveApi + "&lang=" + lang + "&units=metric";
        
        HttpClient client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .build();
        
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                System.out.println("Resposta da API: " + jsonResponse);
                
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

                String descricao = jsonObject.getAsJsonArray("weather")
                        .get(0).getAsJsonObject()
                        .get("description").getAsString();
                
                double temperatura = jsonObject.getAsJsonObject("main")
                        .get("temp").getAsDouble();
                
                double temperaturaMin = jsonObject.getAsJsonObject("main")
                        .get("temp_min").getAsDouble();
                
                double temperaturaMax = jsonObject.getAsJsonObject("main")
                        .get("temp_max").getAsDouble();

                System.out.println("Descrição do clima: " + descricao);
                System.out.println("Temperatura atual: " + temperatura + "°C");
                System.out.println("Temperatura mínima: " + temperaturaMin + "°C");
                System.out.println("Temperatura máxima: " + temperaturaMax + "°C");
            } 
                
            else {
                System.out.println("Erro na requisição. Status: " + response.statusCode());
            }      
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
