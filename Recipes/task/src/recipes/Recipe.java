package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import recipes.security.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Name should not be blank")
    private String name;
    @NotBlank(message = "Description should not be blank")
    private String description;
    @NotBlank(message = "Category should not be blank")
    private String category;
    @NotEmpty(message = "Ingredients should contain at least one item")
    @ElementCollection
    private List<String> ingredients = new ArrayList<>();
    @NotEmpty(message = "Directions should contain at least one item")
    @ElementCollection
    private List<String> directions = new ArrayList<>();
    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
