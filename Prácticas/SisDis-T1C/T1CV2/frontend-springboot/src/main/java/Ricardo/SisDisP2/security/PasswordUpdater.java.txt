package Ricardo.SisDisP2.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import Ricardo.SisDisP2.repository.UsuarioRepository;
import Ricardo.SisDisP2.model.Usuario;

@Component
public class PasswordUpdater implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordUpdater(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        String rawPassword = "admin123";  // Contraseña en texto plano
        String hashedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("Contraseña hasheada para admin123: " + hashedPassword);

        usuarioRepository.findByUsername("admin")
            .ifPresentOrElse(
                usuario -> {
                    usuario.setPassword(hashedPassword);
                    usuarioRepository.save(usuario);
                    System.out.println("Contraseña actualizada correctamente para el usuario 'admin'");
                },
                () -> System.out.println("Advertencia: No se encontró el usuario 'admin' para actualizar")
            );
    }
}