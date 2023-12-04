//package mk.finki.ukim.mk.lab.web;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import mk.finki.ukim.mk.lab.model.Movie;
//import mk.finki.ukim.mk.lab.service.MovieService;
//import org.thymeleaf.context.WebContext;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//import org.thymeleaf.web.IWebExchange;
//import org.thymeleaf.web.servlet.JakartaServletWebApplication;
//
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet
//public class MovieListServlet extends HttpServlet {
//    private final SpringTemplateEngine springTemplateEngine;
//    private final MovieService movieService;
//
//    public MovieListServlet(SpringTemplateEngine springTemplateEngine, MovieService movieService) {
//        this.springTemplateEngine = springTemplateEngine;
//        this.movieService = movieService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<Movie> movies;
//        String searchText = req.getParameter("searchText");
//        String minRatingString = req.getParameter("minRating");
//        if(minRatingString==null || minRatingString.isEmpty()){
//            minRatingString="0";
//        }
//        double minRating = Double.parseDouble(minRatingString);
//
//        if (searchText != null && !searchText.isEmpty() && minRating >= 0) {
//            // If search parameters are provided, filter and display search results
//            movies = movieService.searchMovies(searchText, minRating);
//        } else {
//            // If no search parameters are provided, list all movies
//            movies = movieService.listAll();
//        }
//        IWebExchange exchange = JakartaServletWebApplication
//                .buildApplication(getServletContext())
//                .buildExchange(req, resp);
//        WebContext context= new WebContext(exchange);
//        context.setVariable("movies", movies);
//        springTemplateEngine.process(
//                "listMovies.html",
//                context,
//                resp.getWriter()
//        );
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String title = req.getParameter("radio");
//        int tickets = Integer.parseInt(req.getParameter("numTickets"));
//        if(title!=null && !title.isEmpty() && tickets>0){
//            resp.sendRedirect("/ticketOrder?title=" + title + "&tickets=" + tickets);
//        }else{
//            String searchText=req.getParameter("search");
//            double minRating = Double.parseDouble(req.getParameter("minRating"));
//
//            List<Movie> filteredMovies = movieService.searchMovies(searchText, minRating);
//            IWebExchange exchange = JakartaServletWebApplication
//                    .buildApplication(getServletContext())
//                    .buildExchange(req, resp);
//            WebContext context= new WebContext(exchange);
//            context.setVariable("movies", filteredMovies);
//            springTemplateEngine.process("listMovies.html", context, resp.getWriter());
//
//        }
//    }
//}
