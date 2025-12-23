package rs.fi.appl.olb;

import io.pebbletemplates.pebble.PebbleEngine;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;


@Component
public class Template{
    PebbleEngine pebbleEngine;

    public Template(){
        this.pebbleEngine = new PebbleEngine.Builder().build();
    }

    public String render(String templateName){
        var t = pebbleEngine.getTemplate("templates/"+templateName+".peb");
        Writer writer = new StringWriter();
        try {
            t.evaluate(writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }
}
