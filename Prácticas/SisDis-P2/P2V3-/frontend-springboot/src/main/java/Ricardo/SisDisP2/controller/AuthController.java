package Ricardo.SisDisP2.controller;

import Ricardo.SisDisP2.model.Usuario;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final String API_URL = "http://api-python:5000/usuarios/validar";
    
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Usuario usuario, Model model) {
        try {
            // 1. Preparar la solicitud
            Map<String, String> credentials = new HashMap<>();
            credentials.put("username", usuario.getUsername());
            credentials.put("password", usuario.getPassword());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(credentials, headers);

            // 2. Llamar al API Python
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, request, Map.class);

            // 3. Procesar respuesta
            if (response.getStatusCode() == HttpStatus.OK && 
                response.getBody() != null && 
                Boolean.TRUE.equals(response.getBody().get("valid"))) {
                return "redirect:/pokemon";
            } else {
                model.addAttribute("error", "Credenciales inválidas");
                return "login";
            }
            
        } catch (RestClientException e) {
            model.addAttribute("error", "Error de conexión con el API: " + e.getMessage());
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Error inesperado: " + e.getMessage());
            return "login";
        }
    }
}