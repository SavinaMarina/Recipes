package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeService.findById(id);
    }

    @PostMapping("/new")
    public Map<String, Long> postRecipe(@Valid @RequestBody Recipe recipe) {
        Recipe r = recipeService.save(recipe);
        return Map.of("id", r.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable Long id, @Valid @RequestBody Recipe recipe) {
        recipeService.update(id, recipe);
    }

    @GetMapping("/search")
    public List<Recipe> search(@RequestParam(required = false) String category, @RequestParam(required = false) String name) {
        if (category == null && name == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No parameters specified");
        if (category != null && name != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "More than 1 parameter specified");
        if (category != null)
            return recipeService.searchByCategory(category);
        else
            return recipeService.searchByName(name);
    }

}
