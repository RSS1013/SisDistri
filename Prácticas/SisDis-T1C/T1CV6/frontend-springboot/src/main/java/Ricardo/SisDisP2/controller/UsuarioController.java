package Ricardo.SisDisP2.controller;

import Ricardo.SisDisP2.repository.RolRepository;
import Ricardo.SisDisP2.model.Usuario;
import Ricardo.SisDisP2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.access.prepost.PreAuthorize;

import java.security.Principal;



@Controller
@RequestMapping("/usuarios")
public class UsuarioController {


private boolean isAdmin() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth.getAuthorities().stream()
               .map(GrantedAuthority::getAuthority)
               .anyMatch(role -> role.equals("ROLE_ADMIN"));
}


    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/nuevo")
    public String mostrarFormularioAlta(Model model) {
    model.addAttribute("usuario", new Usuario());
    model.addAttribute("rolesDisponibles", rolRepository.findAll());
    return "usuario-form";
        }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        // Si es nuevo o edición sin cambio de contraseña
        if (!usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        usuarioRepository.save(usuario);
        return "redirect:/usuarios";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
    model.addAttribute("usuario", usuario);
    model.addAttribute("rolesDisponibles", rolRepository.findAll());
    return "usuario-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/perfil")
    public String editarPerfil(Model model, Principal principal) {
       Usuario usuario = usuarioRepository.findByUsername(principal.getName()).orElseThrow();
       model.addAttribute("usuario", usuario);
       return "perfil-form"; // nueva plantilla para editar solo la contraseña
    }

}
