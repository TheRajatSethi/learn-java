package rs.fi.appl.olb.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rs.baas.appl.PublicMain.Template;

@Controller
public class RegistrationWeb {

    @Autowired
    Template template;

    @GetMapping(value = "/become-a-client", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String index(){
        return template.render("register");
    }

}
