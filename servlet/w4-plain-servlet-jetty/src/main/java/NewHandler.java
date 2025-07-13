import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.stream.Collectors;

public class NewHandler extends HttpServlet {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Hitting NewHandler - Do Post");

        BufferedReader reader = req.getReader();

        // Query Params
        System.out.println(req.getParameterMap());

        // Form values.
        var lines = reader.lines()
                .collect(Collectors.joining(System.lineSeparator()));

        System.out.println(lines);

        resp.sendRedirect("/");
    }
}
