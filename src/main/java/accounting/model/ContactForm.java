package accounting.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContactForm {
    @NotBlank(message = "Моля, въведете име")
    @Size(max = 80)
    private String name;


    @NotBlank(message = "Моля, въведете email")
    @Email(message = "Невалиден email адрес")
    private String email;


    @Size(max = 30, message = "Телефонът е твърде дълъг")
    private String phone;


    @NotBlank(message = "Моля, въведете съобщение")
    @Size(max = 2000)
    private String message;


    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }


    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

}
