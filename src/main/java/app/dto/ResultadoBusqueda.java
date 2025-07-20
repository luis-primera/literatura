package app.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultadoBusqueda {
    private List<LibroDto> results;

    public List<LibroDto> getResults() {
        return results;
    }

    public void setResults(List<LibroDto> results) {
        this.results = results;
    }
}
