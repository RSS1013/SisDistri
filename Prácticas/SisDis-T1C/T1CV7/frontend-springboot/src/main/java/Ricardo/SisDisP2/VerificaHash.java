package Ricardo.SisDisP2;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class VerificaHash {
    public static void main(String[] args) {
        String hash = "$2a$10$cM5/Gu25O7E6q4V3GWLC1uqgI9yhPL1ZXcTh5bIFrbYh3v0vX.VWu";
        String password = "admin";

        boolean resultado = BCrypt.checkpw(password, hash);

        System.out.println("¿La contraseña coincide con el hash? " + resultado);
    }
}
