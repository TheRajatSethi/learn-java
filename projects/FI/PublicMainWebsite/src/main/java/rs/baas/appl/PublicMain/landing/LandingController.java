package rs.baas.appl.PublicMain.landing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rs.baas.appl.PublicMain.Template;

@Controller
@RequiredArgsConstructor
public class LandingController {

    private final Template template;


    @GetMapping
    @ResponseBody
    String Index(){
        return template.render("index");
    }


    @GetMapping
    @ResponseBody
    String About(){
        return template.render("about");
    }
}
