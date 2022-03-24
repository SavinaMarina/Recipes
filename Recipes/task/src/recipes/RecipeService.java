package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.security.RecipeModificationForbiddenException;
import recipes.security.UserService;

import java.util.List;

@Service
public class RecipeService {
    final RecipeRepository repository;
    final UserService userService;

    @Autowired
    public RecipeService(RecipeRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public Recipe save(Recipe recipe) {
        recipe.setUser(userService.getCurrentUser());
        return repository.save(recipe);
    }

    public Recipe findById(Long id) {
        return repository.findById(id)
                .orElseThrow(RecipeNotFoundException::new);
    }

    private void checkModificationPermission(Recipe recipe) {
        if (!recipe.getUser().equals(userService.getCurrentUser()))
            throw new RecipeModificationForbiddenException();
    }

    public void deleteById(Long id) {
        Recipe recipeToDelete = findById(id);
        checkModificationPermission(recipeToDelete);
        repository.deleteById(id);
    }

    public void update(Long id, Recipe recipe) {
        Recipe recipeToUpdate = findById(id);
        checkModificationPermission(recipeToUpdate);
        recipeToUpdate.setName(recipe.getName());
        recipeToUpdate.setDescription(recipe.getDescription());
        recipeToUpdate.setCategory(recipe.getCategory());
        recipeToUpdate.setDirections(recipe.getDirections());
        recipeToUpdate.setIngredients(recipe.getIngredients());
        repository.save(recipeToUpdate);
    }

    public List<Recipe> searchByCategory(String category) {
        return repository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> searchByName(String name) {
        return repository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
