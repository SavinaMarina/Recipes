package recipes.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RecipeModificationForbiddenException extends RuntimeException{
    public RecipeModificationForbiddenException() {
        super();
    }
}
