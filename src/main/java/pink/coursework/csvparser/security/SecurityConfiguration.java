package pink.coursework.csvparser.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pink.coursework.csvparser.config.AuthSuccessApplicationListener;
import pink.coursework.csvparser.config.CustomLogoutHandler;

import javax.sql.DataSource;

/**
 * настройка конфигураций spring security
 * @Autowired обеспечивает контроль над тем, где и как автосвязывание должны быть осуществленно.
 * @Override аннотация позволяет переопределить метод у родителя.
 * @Value аннотация позволяет нам использовать значения из вне в поля в bean-компонентах.
 * @Bean аннотация позволяет определить bean-компоненты.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //объект класса BCryptPasswordEncoder для шифрования паролей
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //объект класса DataSource
    @Autowired
    private DataSource dataSource;
    //sql запрос на существование пользователя и его активность
    @Value("${spring.queries.users-query}")
    private String usersQuery;
    //sql запрос на получение ролей пользователя
    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    /**<p>Настройка конфигурации авторизации пользователя в spring security</p>
     * @param auth обьект AuthenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    /**<p>Настройка конфигурации путей страниц в spring security</p>
     * @param http обьект HttpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/login/login").permitAll()
                    .antMatchers("/registration/register").permitAll()
                    .antMatchers("/registration/active/{code}").permitAll()
                    .antMatchers("/user/password").permitAll()
                    .antMatchers("/home/contacts").permitAll()
                    .antMatchers("/home/about").permitAll()
                    .antMatchers("/home/admin/**").hasAuthority("moderator").anyRequest()
                    .authenticated()
                .and()
                    .csrf()
                    .disable()
                    .formLogin()
                    .loginPage("/login/login")
                    .failureUrl("/login/login?error=true")
                    .defaultSuccessUrl("/")
                    .usernameParameter("email")
                    .passwordParameter("password")
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .addLogoutHandler(new CustomLogoutHandler())
                    .logoutSuccessUrl("/login/login").permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/error");
    }

    /**<p>Настройка доступа статических объектов в spring security</p>
     * @param web параметр WebSecurity для настройки статики
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**","/files/**","/fonts/**","/icons_users/**", "/images/**", "/js/**");
    }

    /**<p>Регистрация бина класса-слушателя для пользователя в spring security</p>
     * @return обьект AuthSuccessApplicationListener
     */
    @Bean
    public ApplicationListener applicationListener(){
        return new AuthSuccessApplicationListener();
    }

    /**<p>Регистрация бина класса-обработчика LogoutHandler в spring security</p>
     * @return обьект AuthSuccessApplicationListener
     */
    @Bean
    public LogoutHandler logoutSuccessHandler(){
        return new CustomLogoutHandler();
    }

    /**<p>Регистрация бина класса BCryptPasswordEncoder в spring security</p>
     * @return обьект BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

}
