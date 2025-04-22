package Ricardo.SisDisP2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VistaController {

    @GetMapping("/")
    public String index() {
        return "index"; // index.html en src/main/resources/templates
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas");
        }
        return "login"; // login.html en templates
    }

    @GetMapping("/api-test")
    public String apiTest() {
        return "api-test"; // api-test.html en templates
    }
}
