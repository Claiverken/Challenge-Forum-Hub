package hub.forum.challenge.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hub.forum.challenge.domain.answer.Answer;
import hub.forum.challenge.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "autor")
    @JsonIgnore // Evita recursividade infinita ao serializar para JSON
    private List<Topico> topicos = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore // Evita recursividade infinita
    private List<Answer> answers = new ArrayList<>();

    // Construtor para facilitar a criação de novos usuários
    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Métodos da interface UserDetails a seguir:

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        // Usaremos o e-mail como "username" para o login
        return email;
    }

    // Para simplificar, vamos retornar 'true' para os métodos de status da conta.
    // Em um sistema mais complexo, poderíamos ter colunas no banco para controlar isso.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}