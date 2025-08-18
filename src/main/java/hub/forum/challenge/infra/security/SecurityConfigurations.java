package hub.forum.challenge.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import hub.forum.challenge.infra.exception.TratamentoDeErrosDeAutenticacao;
import hub.forum.challenge.infra.exception.TratamentoDeErroDeAcessoNegado;

@Configuration // Indica ao Spring que esta é uma classe de configuração
@EnableWebSecurity // Habilita e customiza as configurações de segurança
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;
    private final TratamentoDeErrosDeAutenticacao tratamentoDeErrosDeAutenticacao;
    private final TratamentoDeErroDeAcessoNegado tratamentoDeErroDeAcessoNegado;



    public SecurityConfigurations(SecurityFilter securityFilter,
                                  TratamentoDeErrosDeAutenticacao tratamentoDeErrosDeAutenticacao,
                                  TratamentoDeErroDeAcessoNegado tratamentoDeErroDeAcessoNegado) {
        this.securityFilter = securityFilter;
        this.tratamentoDeErrosDeAutenticacao = tratamentoDeErrosDeAutenticacao;
        this.tratamentoDeErroDeAcessoNegado = tratamentoDeErroDeAcessoNegado;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // Permite acesso público ao endpoint de usuarios
                    req.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();

                    // Permite acesso público ao endpoint de login
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();

                    // Permite acesso público para VISUALIZAR os tópicos
                    req.requestMatchers(HttpMethod.GET, "/topicos").permitAll();

                    //Permite acesso público a visualização de um tópico específico
                    req.requestMatchers(HttpMethod.GET, "/topicos/**").permitAll();

                    // Todas as outras requisições (POST, PUT, DELETE, GET /topicos/{id}) exigem autenticação
                    req.anyRequest().authenticated();
                })
                // Adiciona nosso filtro para ser executado antes do filtro padrão do Spring
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint(tratamentoDeErrosDeAutenticacao)
                        .accessDeniedHandler(tratamentoDeErroDeAcessoNegado) // << LINHA ADICIONADA
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}