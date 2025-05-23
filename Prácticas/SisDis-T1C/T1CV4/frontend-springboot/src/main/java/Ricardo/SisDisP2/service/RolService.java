package Ricardo.SisDisP2.service;

import Ricardo.SisDisP2.model.Rol;
import Ricardo.SisDisP2.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Rol getOrCreateRol(String nombreRol) {
        return rolRepository.findByNombre(nombreRol)
                .orElseGet(() -> rolRepository.save(new Rol(nombreRol)));
    }
}
