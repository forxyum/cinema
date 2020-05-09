
import hu.alkfejl.dao.CinemaDAO;
import hu.alkfejl.dao.CinemaDAOImp;
import hu.alkfejl.model.Movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/movie")
public class ListMovieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CinemaDAO dao = new CinemaDAOImp();

        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();
        for(Movie m : dao.listAllMovies()){
            pw.println(m);
        }

    }
}
