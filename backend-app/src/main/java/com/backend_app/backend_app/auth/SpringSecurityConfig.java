package com.backend_app.backend_app.auth;
import com.backend_app.backend_app.auth.filter.JwtAuthenticationFilter;
import com.backend_app.backend_app.auth.filter.JwtValidationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private  JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        return http.authorizeHttpRequests(authz -> authz
                        //Reglas para usuarios
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers("/api/chat/**").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/usuarios/mis-aportes").hasAnyRole("ADMIN", "ASOCIADO")
                        .requestMatchers(HttpMethod.GET,"/api/usuarios/mis-aportes/descargar").hasAnyRole("ADMIN","ASOCIADO")
                        .requestMatchers(HttpMethod.GET,"/api/usuarios/mis-ahorros").hasAnyRole("ADMIN","ASOCIADO")
                        .requestMatchers(HttpMethod.GET,"/api/usuarios/mis-transacciones").hasAnyRole("ADMIN","ASOCIADO")
                        .requestMatchers(HttpMethod.GET,"/api/usuarios/mis-prestamos").hasAnyRole("ADMIN","ASOCIADO")
                        .requestMatchers(HttpMethod.GET,"/api/usuarios/mis-inscripciones-actividades").hasAnyRole("ADMIN","ASOCIADO")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios", "/api/usuarios/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/{identificacion}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/completos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/ultimo-mes-usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/inactivos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/usuarios/refresh").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/{idUsuario}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/usuarios/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/por-fecha").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/por-estado").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/ultimo-mes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/alfabetico").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/listar").hasRole("ADMIN")

                        //Reglas para dashboard
                        .requestMatchers(HttpMethod.GET,"/api/dashboard/Kpis").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/dashboard/transacciones").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/dashboard/tipos-ahorros").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/dashboard/prestamos-estado").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/dashboard/actividades-proximas").hasRole("ADMIN")

                        //Reglas para actividades
                        .requestMatchers(HttpMethod.GET, "/api/actividades", "/api/actividades/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/actividades/{idActividad}").hasAnyRole("ASOCIADO", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/actividades/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/actividades/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/actividades/{idActividad}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/actividades/actividades-asociados").hasAnyRole("ASOCIADO","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/actividades/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/actividades/actividades-programadas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/actividades/completas").hasRole("ADMIN")

                         //Reglas para Beneficios
                        .requestMatchers(HttpMethod.GET, "/api/beneficios", "/api/beneficios/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/beneficios/{idBeneficio}").hasAnyRole("ASOCIADO","ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/beneficios/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/beneficios/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/beneficios/{idBeneficio}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/beneficios/beneficios-asociados").hasAnyRole("ASOCIADO","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/beneficios/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/beneficios/completos").hasRole("ADMIN")

                        //Reglas para categorías
                        .requestMatchers(HttpMethod.GET,"/api/categorias", "/api/categorias/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/categorias/{idCategoria}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/categorias/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categorias/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/categorias/{idCategoria}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/categorias/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/categorias/completas").hasRole("ADMIN")

                         //Reglas para correos
                        .requestMatchers(HttpMethod.GET, "/api/correos", "/api/correos/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/correos/{idCorreo}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/correos/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/correos/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/correos/{idCorreo}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/correos/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/correos/completos").hasRole("ADMIN")

                         //Reglas para cuentas de ahorros
                        .requestMatchers(HttpMethod.GET, "/api/cuentas-ahorros", "/api/cuentas-ahorros/page/{page}").hasAnyRole("ASOCIADO","ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/cuentas-ahorros/mayor").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/cuentas-ahorros/total").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/cuentas-ahorros/promedio-ahorros").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/cuentas-ahorros/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/cuentas-ahorros/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/cuentas-ahorros/{idAhorro}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/cuentas-ahorros/reporte/{idUsuario}").hasAnyRole("ASOCIADO","ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/cuentas-ahorros/top10-mas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/cuentas-ahorros/top10-menos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/cuentas-ahorros/completos").hasRole("ADMIN")

                         //Reglas para Datos Asociados
                        .requestMatchers(HttpMethod.GET, "/api/datos-asociados", "/api/datos-asociados/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/datos-asociados/{idDatosAsociados}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/datos-asociados/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/datos-asociados/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/datos-asociados/{idDatosAsociados}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/datos-asociados/completos").hasRole("ADMIN")

                         //Reglas para Detalle Transaccion
                        .requestMatchers(HttpMethod.GET,"/api/detalle-transaccion", "/api/detalle-transaccion/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/detalle-transaccion/{idDetalle}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/detalle-transaccion/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/detalle-transaccion/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/detalle-transaccion/{idDetalle}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/detalle-transaccion/completas").hasRole("ADMIN")

                         //Reglas para Direcciones
                        .requestMatchers(HttpMethod.GET, "/api/direcciones","/api/direcciones/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/direcciones/{idDireccion}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/direcciones/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/direcciones/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/direcciones/{idDireccion}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/direcciones/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/direcciones/completas").hasRole("ADMIN")

                         //Reglas para Estados
                        .requestMatchers(HttpMethod.GET,"/api/estados","/api/estados/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/estados/{idEstado}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/estados/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/estados/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/estados/completos").hasRole("ADMIN")

                         //Reglas para Inscripciones Actividades
                        .requestMatchers(HttpMethod.GET,"/api/inscripciones-actividades","/api/inscripciones-actividades/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/inscripciones-actividades/{idInscripcion}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/inscripciones-actividades/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/inscripciones-actividades/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/inscripciones-actividades/{idInscripcion}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/inscripciones-actividades/completas").hasRole("ADMIN")

                         //Reglas para Lugares Eventos
                        .requestMatchers(HttpMethod.GET, "/api/lugar-evento","/api/lugar-evento/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/lugar-evento/{idLugarEvento}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/lugar-evento/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/lugar-evento/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/lugar-evento/{idLugarEvento}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/lugar-evento/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/lugar-evento/completos").hasRole("ADMIN")

                         //Reglas para Modulos de Reportes
                        .requestMatchers(HttpMethod.GET,"/api/modulo-reporte","/api/modulo-reporte/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/modulo-reporte/{idModulo}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/modulo-reporte/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/modulo-reporte/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/modulo-reporte/{idModulo}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/modulo-reporte/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/modulo-reporte/completos").hasRole("ADMIN")

                         //Reglas para Movimientos Ahorros
                        .requestMatchers(HttpMethod.GET,"/api/movimientos-ahorros","/api/movimientos-ahorros/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/movimientos-ahorros/{idMovimiento}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/movimientos-ahorros/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/movimientos-ahorros/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/movimientos-ahorros/completos").hasRole("ADMIN")

                        //Reglas para Pagos Prestamos
                        .requestMatchers(HttpMethod.GET,"/api/pagos-prestamos","/api/pagos-prestamos/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/pagos-prestamos/{idPago}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/pagos-prestamos/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/pagos-prestamos/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/pagos-prestamos/completos").hasRole("ADMIN")

                        //Reglas para Prestamos
                        .requestMatchers(HttpMethod.GET,"/api/prestamos","/api/prestamos/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/prestamos/{idPrestamo}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/prestamos/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/prestamos/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/prestamos/{idPrestamo}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/prestamos/estado").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/prestamos/completos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/prestamos/por-estado").hasRole("ADMIN")

                        //Reglas para Puestos Empresas
                        .requestMatchers(HttpMethod.GET, "/api/puestos-empresas","/api/puestos-empresas/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/puestos-empresas/{idPuestoEmpresa}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/puestos-empresas/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/puestos-empresas/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/puestos-empresas/{idPuestoEmpresa}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/puestos-empresas/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/puestos-empresas/completos").hasRole("ADMIN")

                        //Reglas para Reportes
                        .requestMatchers(HttpMethod.GET, "/api/reportes","/api/reportes/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/reportes/{idReporte}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/reportes/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/reportes/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/reportes/{idReporte}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/reportes/reporte/pdf").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/reportes/completos").hasRole("ADMIN")


                        //Reglas para Resultados Reporte
                        .requestMatchers(HttpMethod.GET,"/api/resultados-reportes","/api/resultados-reportes/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/resultados-reportes/{idResultado}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/resultados-reportes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/resultados-reportes/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/resultados-reportes/completos").hasRole("ADMIN")

                        //Reglas para Roles
                        .requestMatchers(HttpMethod.GET,"/api/roles").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/roles/{idRol}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/roles/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/roles/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/roles/{idRol}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/roles/completos").hasRole("ADMIN")

                        //Reglas para Servicios
                        .requestMatchers(HttpMethod.GET,"/api/servicios","/api/servicios/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/servicios/{idServicio}").hasAnyRole("ASOCIADO","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/servicios/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/servicios/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/servicios/{idServicio}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/servicios/servicios-asociados").hasAnyRole("ASOCIADO","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/servicios/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/servicios/completos").hasRole("ADMIN")

                        //Reglas para Telefonos
                        .requestMatchers(HttpMethod.GET,"/api/telefonos","/api/telefonos/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/telefonos/{idTelefono}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/telefonos/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/telefonos/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/telefonos/{idTelefono}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/telefonos/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/telefonos/completos").hasRole("ADMIN")

                        //Reglas para Tipos de Ahorro
                        .requestMatchers(HttpMethod.GET,"/api/tipos-ahorros","/api/tipos-ahorros/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/tipos-ahorros/{idTipoAhorro}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/tipos-ahorros/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/tipos-ahorros/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/tipos-ahorros/{idTipoAhorro}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/tipos-ahorros/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/tipos-ahorros/completos").hasRole("ADMIN")

                        //Reglas para Tipos de Reportes
                        .requestMatchers(HttpMethod.GET,"/api/tipo-reportes","/api/tipo-reportes/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/tipo-reportes/{idTipoReporte}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/tipo-reportes/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/tipo-reportes/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/tipo-reportes/{idTipoReporte}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/tipo-reportes/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/tipo-reportes/completos").hasRole("ADMIN")

                        //Reglas para Tipos de Transacciones
                        .requestMatchers(HttpMethod.GET,"/api/tipo-transacciones","/api/tipo-transacciones/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/tipo-transacciones/{idTipoTransaccion}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/tipo-transacciones/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/tipo-transacciones/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/tipo-transacciones/{idTipoTransaccion}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/tipo-transacciones/buscar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/tipo-transacciones/completas").hasRole("ADMIN")

                        //Reglas para Transacciones
                        .requestMatchers(HttpMethod.GET,"/api/transacciones/historial-transacciones").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/transacciones","/api/transacciones/page/{page}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/transacciones/{idTransaccion}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/transacciones/guardar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/transacciones/actualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/transacciones/{idTransaccion}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/transacciones/cantidad/{idTransaccion}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/transacciones/top5").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/transacciones/historial/{idUsuario}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/transacciones/completos").hasRole("ADMIN")

                        .anyRequest().authenticated())
                .addFilterBefore(new org.springframework.web.filter.CorsFilter(configurationSource()), JwtAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(configurationSource()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    CorsConfigurationSource configurationSource() {
        CorsConfiguration config = new CorsConfiguration();
      //  config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedOriginPatterns(Arrays.asList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<CorsFilter>(
                new CorsFilter(this.configurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}
