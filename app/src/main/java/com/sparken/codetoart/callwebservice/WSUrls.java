package com.sparken.codetoart.callwebservice;


public interface WSUrls {

    String BASE_URL = "http://192.168.0.20/durodemo/";

    String GET_ROUTE_DATA = BASE_URL + "get_routes.php";

    String GET_ROUTEWISE_SITES = BASE_URL + "get_sites.php";

    String URL_MOVIE_IMAGE_LIST = "https://image.tmdb.org/t/p/w500/";
    String URL_MOVIES = "https://api.themoviedb.org/3/movie/upcoming";

    String URL_MOVIE_DETAILS = "https://api.themoviedb.org/3/movie/";

}
