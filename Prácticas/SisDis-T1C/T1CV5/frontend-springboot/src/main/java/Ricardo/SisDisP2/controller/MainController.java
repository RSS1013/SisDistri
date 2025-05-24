package Ricardo.SisDisP2.controller;

import Ricardo.SisDisP2.model.Usuario;
import Ricardo.SisDisP2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;

@Controller
public class MainController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas");
        }
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @GetMapping("/api-test")
    public String apiTest(Model model) {
        model.addAttribute("pokemonList",
                Arrays.asList("pikachu", "charizard", "bulbasaur", "squirtle"));
        return "api-test";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado";
    }


    // Cargar usuario por defecto al iniciar la aplicación
    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña hasheada
                usuarioRepository.save(admin);
                System.out.println("Usuario 'admin' creado con contraseña 'admin123'");
            }
        };
    }

    // Configuración de recursos estáticos
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
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
