package lab.space.my_house_24_user.model.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[А-ЯЄІЇҐЁA-Z][а-яєіїґёa-z]*$",message = "{pattern.name.message}")
        @Size(max = 25, message = "{size.less.message}"+" 25")
        String lastname,

        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[А-ЯЄІЇҐЁA-Z][а-яєіїґёa-z]*$",message = "{pattern.name.message}")
        @Size(max = 25, message = "{size.less.message}"+" 25")
        String firstname,
        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[А-ЯЄІЇҐЁA-Z][а-яєіїґёa-z]*$",message = "{pattern.name.message}")
        @Size(max = 25, message = "{size.less.message}"+" 25")
        String surname,

        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
        @Pattern(message = "{pattern.email.message}", regexp = "^((([0-9A-Za-z]{1}[-0-9A-z\\.]{0,30}[0-9A-Za-z]?))@([-A-Za-z]{1,}\\.){1,}[-A-Za-z]{2,})$")
        String email,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 20 , message = "{size.less.message}"+" 20")
        @Size(min = 4 , message = "{size.greater.message}"+" 4")
        String password,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 20, message = "{size.less.message}"+" 20")
        @Size(min = 4 , message = "{size.greater.message}"+" 4")
        String confirmPassword,

        Boolean confirm
) {
}
