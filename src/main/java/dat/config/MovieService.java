package dat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.MovieDTO;
import dat.dtos.MovieListDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService {
    private static final String API_KEY = System.getenv("api_key");
    private static final String BASE_URL_MOVIE = "https://api.themoviedb.org/3/movie/";
    private static final String BASE_URL_DISCOVER = "https://api.themoviedb.org/3/discover/movie";
    static ObjectMapper om = new ObjectMapper();

    public static String getMovieById(int id) throws IOException, InterruptedException {
        String url = BASE_URL_MOVIE + id + "?api_key=" + API_KEY;
        om.registerModule(new JavaTimeModule());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        MovieDTO movieDTO = om.readValue(response.body(), MovieDTO.class);

        System.out.println(movieDTO.getRelease_date().getYear());

        System.out.println(movieDTO);
        return movieDTO.toString();
    }

    public static String getMovieByReleaseYear(int releaseYear) throws IOException, InterruptedException {
        String url = BASE_URL_DISCOVER + "?api_key=" + API_KEY + "&page=2";
        om.registerModule(new JavaTimeModule());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        MovieListDTO movieDTOList = om.readValue(response.body(), MovieListDTO.class);
        List<MovieDTO> movieDTOS = movieDTOList.getResults();

        boolean found = false;

        for (MovieDTO movieDTO : movieDTOS) {
            if (movieDTO.getRelease_date().getYear() == releaseYear) {
                System.out.println(movieDTO);
                found = true;
            }
        }

        if(!found) {
            System.out.println("No movies found for the year: " + releaseYear);
        }

        return response.body();
    }

    public static String getMovieByRating(double lowRating, double highRating) throws IOException, InterruptedException {
        String url = BASE_URL_DISCOVER + "?api_key=" + API_KEY + "&page=1";
        om.registerModule(new JavaTimeModule());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        MovieListDTO movieDTOList = om.readValue(response.body(), MovieListDTO.class);
        List<MovieDTO> movieDTOS = movieDTOList.getResults();

        boolean found = false;

        for (MovieDTO movieDTO : movieDTOS) {
            if (movieDTO.getVote_average() >= lowRating && movieDTO.getVote_average() <= highRating) {
                System.out.println(movieDTO);
                found = true;
            }
        }

        if(!found) {
            System.out.println("No movies have a rating between: " + lowRating + " and " + highRating);
        }

        return response.body();
    }

    public static String filterMoviesByRealeaseYear() throws IOException, InterruptedException {
        String url = BASE_URL_DISCOVER + "?api_key=" + API_KEY + "&page=20";
        om.registerModule(new JavaTimeModule());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        MovieListDTO movieDTOList = om.readValue(response.body(), MovieListDTO.class);
        List<MovieDTO> movieDTOS = movieDTOList.getResults();

        List<MovieDTO> filterMovies = movieDTOS.stream().sorted(Comparator.comparing(MovieDTO::getRelease_date).reversed()).collect(Collectors.toList());

        System.out.println(filterMovies);
        return response.body();
    }
}
