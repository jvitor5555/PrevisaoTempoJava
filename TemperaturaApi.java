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
        
        // Chave da API (substitua pela sua chave real)
        String chaveApi = "3c81d4265b989ef7b4dbe86b272b9670";
        
        // Nome da cidade
        String cidade = "São Paulo";
        
        // Codificando a cidade para garantir que espaços e caracteres especiais sejam tratados corretamente
        String cidadeCodificada = URLEncoder.encode(cidade, StandardCharsets.UTF_8);
        
        // Idioma para a resposta (português)
        String lang = "pt_br";
        
        // Construindo a URL com a cidade codificada
        String link = "https://api.openweathermap.org/data/2.5/weather?q=" + cidadeCodificada + "&appid=" + chaveApi + "&lang=" + lang + "&units=metric";
        
        // Criando o cliente HTTP
        HttpClient client = HttpClient.newHttpClient();
        
        // Criando a requisição HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .build();
        
        try {
            // Enviando a requisição e recebendo a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            // Se a resposta for 200 (OK)
            if (response.statusCode() == 200) {
                // Corpo da resposta em formato JSON
                String jsonResponse = response.body();
                System.out.println("Resposta da API: " + jsonResponse);
                
                // Usando o Gson para parsear o JSON
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

                // Extraindo dados específicos do JSON
                String descricao = jsonObject.getAsJsonArray("weather")
                        .get(0).getAsJsonObject()
                        .get("description").getAsString();
                
                double temperatura = jsonObject.getAsJsonObject("main")
                        .get("temp").getAsDouble();
                
                double temperaturaMin = jsonObject.getAsJsonObject("main")
                        .get("temp_min").getAsDouble();
                
                double temperaturaMax = jsonObject.getAsJsonObject("main")
                        .get("temp_max").getAsDouble();

                // Exibindo as informações extraídas da API
                System.out.println("Descrição do clima: " + descricao);
                System.out.println("Temperatura atual: " + temperatura + "°C");
                System.out.println("Temperatura mínima: " + temperaturaMin + "°C");
                System.out.println("Temperatura máxima: " + temperaturaMax + "°C");
            } else {
                // Se a resposta da API não for 200, exibe o erro
                System.out.println("Erro na requisição. Status: " + response.statusCode());
            }      
        } catch (Exception e) {
            e.printStackTrace(); // Exibe a pilha de erros caso algo dê errado
        }
    }
}
