package rs.baas.appl.PublicMain.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rs.baas.appl.PublicMain.Template;


@Controller
public class KbWeb {

    @Autowired
    Template template;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index(){
        return template.render("kb");
    }

}
