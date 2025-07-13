import io.pebbletemplates.pebble.template.PebbleTemplate;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class IndexHandler extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        logger.info("Hitting Index Handler - Do Get");

        PebbleTemplate compiledTemplate = Templates.engine.getTemplate("templates/index.html");
        Writer writer = new StringWriter();
        Map<String, Object> context = new HashMap<>();
        context.put("name", "Sam");
        compiledTemplate.evaluate(writer, context);
        resp.getWriter().write(writer.toString());

    }

}
