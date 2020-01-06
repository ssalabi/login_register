//package com.example.myapplication;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.http.GET;
//import retrofit2.http.Path;
//public final class GitHubService{
//    public static final String API_URL = "https://api.github.com";
//
//    public static class Contributor {
//        public final String login;
//        public final int contributions;
//
//        public Contributor(String login, int contributions) {
//            this.login = login;
//            this.contributions = contributions;
//        }
//    }
//
//    public interface GitHub {
//        @GET("/repos/{owner}/{repo}/contributors")
//        Call<List<Contributor>> contributors(
//                @Path("owner") String owner,
//                @Path("repo") String repo);
//    }
//}