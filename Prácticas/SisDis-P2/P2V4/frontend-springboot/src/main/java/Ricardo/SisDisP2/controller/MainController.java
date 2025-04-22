package Ricardo.SisDisP2.controller;

import Ricardo.SisDisP2.model.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Arrays;

@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Usuario usuario, Model model) {
        if ("admin".equals(usuario.getUsername()) && "admin123".equals(usuario.getPassword())) {
            return "redirect:/api-test";
        } else {
            model.addAttribute("error", "Credenciales inválidas");
            return "login";
        }
    }

    @GetMapping("/api-test")
    public String apiTest(Model model) {
        model.addAttribute("pokemonList", 
            Arrays.asList("pikachu", "charizard", "bulbasaur", "squirtle"));
        return "api-test";
    }

    // Agregar configuración de recursos estáticos
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Configura la ruta de los recursos estáticos (CSS, JS, imágenes, etc.)
                registry.addResourceHandler("/css/**")
                    .addResourceLocations("classpath:/static/css/");
                registry.addResourceHandler("/js/**")
                    .addResourceLocations("classpath:/static/js/");
                registry.addResourceHandler("/images/**")
                    .addResourceLocations("classpath:/static/images/");
            }
        };
    }
}
