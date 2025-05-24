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
import org.springframework.beans.factory.annotation.Value;

import java.security.Principal;
import java.util.Arrays;

@Controller
public class MainController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(Model model, @Value("${google.maps.api.key}") String apiKey) {
        model.addAttribute("googleApiKey", apiKey);
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

    @GetMapping("/webchat")
    public String webchat() {
        return "webchat";
    }

    // Mostrar formulario de perfil
    @GetMapping("/perfil")
    public String perfilUsuario(Model model, Principal principal) {
        Usuario usuario = usuarioRepository.findByUsername(principal.getName()).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "perfil-form";
    }

    // Guardar nueva contraseña
    @PostMapping("/perfil/guardar")
    public String guardarPerfil(@ModelAttribute Usuario usuario, Principal principal) {
        Usuario actual = usuarioRepository.findByUsername(principal.getName()).orElseThrow();
        actual.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(actual);
        return "redirect:/dashboard?actualizado";
    }

    // Cargar usuario por defecto al iniciar la aplicación
    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
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

