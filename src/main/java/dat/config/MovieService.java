package dat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

public class MovieService {
    private static final String API_KEY = System.getenv("api_key");
    private static final String BASE_URL_MOVIE = "https://api.themoviedb.org/3/movie/";
    private static final String BASE_URL_DISCOVER = "https://api.themoviedb.org/3/discover/movie";
    private static final String GENRE_LIST_URL = "https://api.themoviedb.org/3/genre/movie/list?language=en&api_key=" + API_KEY;
    private static final String BASE_URL_CREDITS = "https://api.themoviedb.org/3/movie/";
    private static final String BASE_URL_COUNTRY = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US";
    static ObjectMapper om = new ObjectMapper();

    public static List<String> goThroughAllPages(String urlWeUse) throws IOException, InterruptedException {
        List<String> allMoviesFromAllPages = new ArrayList<>();
        String url = urlWeUse + "&page=%d" + "&primary_release_date.gte=2019-01-01&sort_by=popularity.desc&with_origin_country=DK";
        HttpClient client = HttpClient.newHttpClient();

        for (int page = 1; page <= 66; page++) {

            String forLoopUrl = String.format(url, page);
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create(forLoopUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            allMoviesFromAllPages.add(response.body());
        }

        return allMoviesFromAllPages;
    }

    private static Map<Integer, String> getAllTheDamnGenres() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(GENRE_LIST_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        GenreListDTO genreListDTO = om.readValue(response.body(), GenreListDTO.class);

        return genreListDTO.getGenres().stream()
                .collect(Collectors.toMap(GenreDTO::getId, GenreDTO::getName));
    }

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

    public static String getDanishMovieFrom2019Plus(String original_language) throws IOException, InterruptedException {
        String url = BASE_URL_COUNTRY + "&api_key=" + API_KEY;
        List<String> allPages = goThroughAllPages(url);
        Map<Integer, String> genreMap = getAllTheDamnGenres();
        om.registerModule(new JavaTimeModule());

        // Api'en er sat til at sortere efter år 2019 og danske film, så derfor er det kun film fra 2019 og frem der bliver vist.
        for (String pageResponse : allPages) {
            MovieListDTO movieDTOList = om.readValue(pageResponse, MovieListDTO.class);
            List<MovieDTO> movieDTOS = movieDTOList.getResults();

            for (MovieDTO movieDTO : movieDTOS) {
                List<String> genreNames = movieDTO.genreHelper(genreMap);

                MovieDTO updatedMovieDTO = MovieDTO.builder()
                        .id(movieDTO.getId())
                        .original_title(movieDTO.getOriginal_title())
                        .genre_ids(movieDTO.getGenre_ids())
                        .genres(genreNames)
                        .original_language(movieDTO.getOriginal_language())
                        .release_date(movieDTO.getRelease_date())
                        .overview(movieDTO.getOverview())
                        .build();
                System.out.println(updatedMovieDTO);
            }
        }

        return "Danske film fra 2019 og op.";
    }



    /*public static String getMovieByRating(double lowRating, double highRating) throws IOException, InterruptedException {
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

     */

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

    public static String getMovieCreditsByMovieID(int id) throws IOException, InterruptedException {
        String url = BASE_URL_CREDITS + id + "/credits" + "?api_key=" + API_KEY;
        om.registerModule(new JavaTimeModule());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        CreditListDTO creditListDTO = om.readValue(response.body(), CreditListDTO.class);
        List<CreditDTO> creditCastDTOS = creditListDTO.getCast();
        List<CreditDTO> creditCrewDTOS = creditListDTO.getCrew();

        List<CreditDTO> allCredits = new ArrayList<>();
        allCredits.addAll(creditCastDTOS);
        allCredits.addAll(creditCrewDTOS);

        for (CreditDTO creditDTO : allCredits) {
            if (creditDTO.getKnown_for_department().equals("Acting")) {
                System.out.println("Cast: " + creditDTO);
            } else if (creditDTO.getKnown_for_department().equals("Directing") && creditDTO.getJob().equals("Director")) {
                System.out.println("Crew: " + creditDTO);
            }
        }

        return creditCastDTOS.toString();
    }
}
