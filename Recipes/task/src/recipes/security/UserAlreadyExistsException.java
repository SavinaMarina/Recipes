package recipes.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User already exists")
public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException() {
    }
}
