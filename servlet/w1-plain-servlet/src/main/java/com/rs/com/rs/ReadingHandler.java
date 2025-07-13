package com.rs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.function.Predicate;

public class ReadingHandler extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ReadingHandler.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var br = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null){
            sb.append(line);
        }
        var reqPayload = sb.toString();

        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());

        SensorReading sr;

        try {
            sr = objectMapper.readValue(reqPayload, SensorReading.class);
            logger.info(String.valueOf(sr));
            if(IsValid.test(sr)){
                resp.setStatus(HttpStatus.OK_200);
                resp.getWriter().println("Ok");
                logger.debug("valid");
            }else {
                resp.setStatus(HttpStatus.BAD_REQUEST_400);
                resp.getWriter().println("Bad Request");
                logger.debug("in-valid");
            }
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().println("Bad Request");
        }
        resp.addHeader("Content-Type", "plain/text");
    }

    Predicate<SensorReading> IsValid = sr -> {
        if (sr.location().latitude() < -90.0 || sr.location().latitude() > 90.0) {
            logger.info("Invalid Latitude");
            return false;
        }
        if (sr.location().longitude() < -180.0 || sr.location().longitude() > 180.0) {
            logger.info("Invalid Longitude");
            return false;
        }
        return !sr.timestamp().isBefore(LocalDateTime.now().minusYears(1)); // TODO --> to be changed for production
    };
}
