package Ricardo.SisDisP2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import java.util.Arrays;


@Controller
@RequestMapping("/pokemon")
public class PokemonController {

    @GetMapping("")
    public String apiTest(Model model) {
        model.addAttribute("pokemonList", 
            Arrays.asList("pikachu", "charizard", "bulbasaur", "squirtle"));
        return "api-test";
    }
}
