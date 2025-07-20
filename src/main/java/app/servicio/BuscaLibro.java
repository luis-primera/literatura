package app.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import app.dto.LibroDto;
import app.dto.ResultadoBusqueda;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class BuscaLibro {
    public LibroDto obtenerLibro (String tituloLibro) {
        String libroCodificado = URLEncoder.encode(tituloLibro, StandardCharsets.UTF_8);
        String url = "https://gutendex.com/books/?search=" + libroCodificado.trim();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            ResultadoBusqueda resultadoBusqueda = mapper.readValue(response.body(), ResultadoBusqueda.class);

            for (LibroDto libro : resultadoBusqueda.getResults()){
                libro.toString();
                return libro;
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
