import { Routes } from '@angular/router';
import { EstadoComponent } from './components/estado/estado.component';
import { ActividadComponent } from './components/actividad/actividad.component';
import { HomeComponent } from './components/home/home.component';
import { BeneficioComponent } from './components/beneficio/beneficio.component';
import { TransaccionComponent } from './components/transaccion/transaccion.component';
import { ServicioComponent } from './components/servicio/servicio.component';
import { AhorroComponent } from './components/ahorro/ahorro.component';
import { UsuarioComponent } from './components/usuario/usuario.component';
import { PrestamoComponent } from './components/prestamo/prestamo.component';
import { ReporteComponent } from './components/reporte/reporte.component';
import { CategoriaComponent } from './components/categoria/categoria.component';
import { CorreoComponent } from './components/correo/correo.component';
import { DatosAsociadosComponent } from './components/datos-asociados/datos-asociados.component';
import { DetalleTransaccionComponent } from './components/detalle-transaccion/detalle-transaccion.component';
import { DireccionComponent } from './components/direccion/direccion.component';
import { InscripcionPorActividadService } from './services/inscripciones-actividad.service';
import { InscripcionesActividadComponent } from './components/inscripciones-actividad/inscripciones-actividad.component';
import { LugarEventoComponent } from './components/lugar-evento/lugar-evento.component';
import { ModuloReporteComponent } from './components/modulo-reporte/modulo-reporte.component';
import { MovimientosAhorroComponent } from './components/movimientos-ahorro/movimientos-ahorro.component';
import { PagosPrestamosComponent } from './components/pagos-prestamos/pagos-prestamos.component';
import { PuestoEmpresaComponent } from './components/puesto-empresa/puesto-empresa.component';
import { ResultadoReporteComponent } from './components/resultado-reporte/resultado-reporte.component';
import { RolComponent } from './components/rol/rol.component';
import { TelefonoComponent } from './components/telefono/telefono.component';
import { TipoAhorroComponent } from './components/tipo-ahorro/tipo-ahorro.component';
import { TipoReporteComponent } from './components/tipo-reporte/tipo-reporte.component';
import { TipoTransaccionComponent } from './components/tipo-transaccion/tipo-transaccion.component';
import { UsuarioFormComponent } from './components/usuario-form/usuario-form.component';
import { CategoriaFormComponent } from './components/categoria-form/categoria-form.component';
import { CorreoFormComponent } from './components/correo-form/correo-form.component';
import { TelefonoFormComponent } from './components/telefono-form/telefono-form.component';
import { PuestoEmpresaFormComponent } from './components/puesto-empresa-form/puesto-empresa-form.component';
import { DatosAsociadosFormComponent } from './components/datos-asociados-form/datos-asociados-form.component';
import { EstadoFormComponent } from './components/estado-form/estado-form.component';
import { DireccionFormComponent } from './components/direccion-form/direccion-form.component';
import { RolFormComponent } from './components/rol-form/rol-form.component';
import { TipoAhorroFormComponent } from './components/tipo-ahorro-form/tipo-ahorro-form.component';
import { AhorroFormComponent } from './components/ahorro-form/ahorro-form.component';
import { LugarEventoFormComponent } from './components/lugar-evento-form/lugar-evento-form.component';
import { ActividadFormComponent } from './components/actividad-form/actividad-form.component';
import { BeneficioFormComponent } from './components/beneficio-form/beneficio-form.component';
import { ServicioFormComponent } from './components/servicio-form/servicio-form.component';
import { PrestamoFormComponent } from './components/prestamo-form/prestamo-form.component';
import { TipoTransaccionFormComponent } from './components/tipo-transaccion-form/tipo-transaccion-form.component';
import { TransaccionFormComponent } from './components/transaccion-form/transaccion-form.component';
import { InscripcionesActividadFormComponent } from './components/inscripciones-actividad-form/inscripciones-actividad-form.component';
import { TipoReporteFormComponent } from './components/tipo-reporte-form/tipo-reporte-form.component';
import { ModuloReporteFormComponent } from './components/modulo-reporte-form/modulo-reporte-form.component';
import { ReporteFormComponent } from './components/reporte-form/reporte-form.component';
import { DetalleFormComponent } from './components/detalle-transaccion-form/detalle-transaccion-form.component';
import { PagoFormComponent } from './components/pagos-prestamos-form/pagos-prestamos-form.component';
import { ResultadoReporteFormComponent } from './components/resultado-reporte-form/resultado-reporte-form.component';
import { AuthComponent } from './auth_component/auth/auth.component';
import { LayoutComponent } from './auth_layout/layout/layout.component';
import { MainLayoutComponent } from './main-layout/main-layout.component';
import { MovimientosAhorroFormComponent } from './components/movimientos-ahorro-form/movimientos-ahorro-form.component';
import { authGuard } from './guards/auth.guard';
import { BeneficioAsociadoComponent } from './components/beneficio-asociado/beneficio-asociado.component';
import { ActividadAsociadoComponent } from './components/actividad-asociado/actividad-asociado.component';
import { ServicioAsociadoComponent } from './components/servicio-asociado/servicio-asociado.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

export const routes: Routes = [
 
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '',         redirectTo: 'login', pathMatch: 'full' },
      { path: 'login',    component: AuthComponent }
    ]
  },
 
  {
    path: 'app',
    component: MainLayoutComponent,
     canActivate: [authGuard],   
    children: [
      { path: '',     redirectTo: 'home', pathMatch: 'full' },
      { path: 'home', component: HomeComponent },
 
      // Estados
      { path: 'estados',                              component: EstadoComponent, canActivate: [authGuard] },
      { path: 'estados/registrar-estado',             component: EstadoFormComponent, canActivate: [authGuard] },
      { path: 'estados/editar-estado/:idEstado',      component: EstadoFormComponent, canActivate: [authGuard] },
 
      { path: 'dashboard',                            component: DashboardComponent, canActivate: [authGuard]},

      // Actividades
      { path: 'actividades',                                  component: ActividadComponent, canActivate: [authGuard] },
      { path: 'actividades/registrar-actividad',              component: ActividadFormComponent, canActivate: [authGuard] },
      { path: 'actividades/editar-actividad/:idActividad',    component: ActividadFormComponent, canActivate: [authGuard] },
      { path: 'actividades/page/:page',                       component: ActividadComponent, canActivate: [authGuard]},
      { path: 'actividades-asociados', component: ActividadAsociadoComponent, canActivate: [authGuard]},
 
      // Beneficios
      { path: 'beneficios',                                   component: BeneficioComponent, canActivate: [authGuard] },
      { path: 'beneficios/registrar-beneficio',               component: BeneficioFormComponent, canActivate: [authGuard] },
      { path: 'beneficios/editar-beneficio/:idBeneficio',     component: BeneficioFormComponent, canActivate: [authGuard] },
      { path: 'beneficios/page/:page',                        component: BeneficioComponent, canActivate: [authGuard]},
      { path: 'beneficios-asociados', component: BeneficioAsociadoComponent, canActivate: [authGuard]},
 
      // Transacciones
      { path: 'transacciones',                                      component: TransaccionComponent, canActivate: [authGuard] },
      { path: 'transacciones/registrar-transaccion',                component: TransaccionFormComponent, canActivate: [authGuard] },
      { path: 'transacciones/editar-transaccion/:idTransaccion',    component: TransaccionFormComponent, canActivate: [authGuard] },
      { path: 'transacciones/page/:page',                           component: TransaccionComponent, canActivate: [authGuard] },
 
      // Servicios
      { path: 'servicios',                                component: ServicioComponent, canActivate: [authGuard] },
      { path: 'servicios/registrar-servicio',             component: ServicioFormComponent, canActivate: [authGuard] },
      { path: 'servicios/editar-servicio/:idServicio',    component: ServicioFormComponent, canActivate: [authGuard] },
      { path: 'servicios/page/:page',                     component: ServicioComponent, canActivate: [authGuard]},
      { path: 'servicios-asociados', component: ServicioAsociadoComponent, canActivate: [authGuard]},


      // Cuentas de Ahorros
      { path: 'cuentas-ahorros',                                    component: AhorroComponent, canActivate: [authGuard] },
      { path: 'cuentas-ahorros/registrar-cuenta-ahorro',            component: AhorroFormComponent, canActivate: [authGuard] },
      { path: 'cuentas-ahorros/editar-cuenta-ahorro/:idAhorro',     component: AhorroFormComponent, canActivate: [authGuard] },
      { path: 'cuentas-ahorros/page/:page',                         component: AhorroComponent, canActivate: [authGuard] },
 
      // Usuarios
      { path: 'usuarios',                                 component: UsuarioComponent, canActivate: [authGuard] },
      { path: 'usuarios/registrar-usuario',               component: UsuarioFormComponent, canActivate: [authGuard] },
      { path: 'usuarios/editar-usuario/:idUsuario',       component: UsuarioFormComponent, canActivate: [authGuard] },
      { path: 'usuarios/page/:page',                      component: UsuarioComponent, canActivate: [authGuard] },
 
      // Prestamos
      { path: 'prestamos',                                  component: PrestamoComponent, canActivate: [authGuard] },
      { path: 'prestamos/registrar-prestamo',               component: PrestamoFormComponent, canActivate: [authGuard] },
      { path: 'prestamos/editar-prestamo/:idPrestamo',      component: PrestamoFormComponent, canActivate: [authGuard] },
      { path: 'prestamos/page/:page',                       component: PrestamoComponent, canActivate: [authGuard]},
 
      // Reportes
      { path: 'reportes',                                 component: ReporteComponent, canActivate: [authGuard] },
      { path: 'reportes/registrar-reporte',               component: ReporteFormComponent, canActivate: [authGuard] },
      { path: 'reportes/editar-reporte/:idReporte',       component: ReporteFormComponent, canActivate: [authGuard] },
      { path: 'reportes/page/:page',                      component: ReporteComponent, canActivate: [authGuard]},
 
      // Categorías
      { path: 'categorias',                                   component: CategoriaComponent, canActivate: [authGuard] },
      { path: 'categorias/registrar-categoria',               component: CategoriaFormComponent, canActivate: [authGuard] },
      { path: 'categorias/editar-categoria/:idCategoria',     component: CategoriaFormComponent, canActivate: [authGuard] },
      { path: 'categorias/page/:page',                        component: CategoriaComponent, canActivate: [authGuard]},
 
      // Correos
      { path: 'correos',                              component: CorreoComponent, canActivate: [authGuard] },
      { path: 'correos/registrar-correo',             component: CorreoFormComponent, canActivate: [authGuard] },
      { path: 'correos/editar-correo/:idCorreo',      component: CorreoFormComponent, canActivate: [authGuard] },
      { path: 'correos/page/:page',                         component: TelefonoComponent, canActivate: [authGuard] },

 
      // Datos Asociados
      { path: 'datos-asociados',                                            component: DatosAsociadosComponent, canActivate: [authGuard] },
      { path: 'datos-asociados/insertar-dato-asociado',                     component: DatosAsociadosFormComponent, canActivate: [authGuard] },
      { path: 'datos-asociados/actualizar-dato-asociado/:idDatosAsociados', component: DatosAsociadosFormComponent, canActivate: [authGuard] },
      { path: 'datos-asociados/page/:page',                                 component: DatosAsociadosComponent, canActivate: [authGuard]},
 
      // Detalle Transacción
      { path: 'detalle-transaccion',                                        component: DetalleTransaccionComponent, canActivate: [authGuard] },
      { path: 'detalle-transaccion/registrar-detalle-transaccion',          component: DetalleFormComponent, canActivate: [authGuard] },
      { path: 'detalle-transaccion/editar-detalle-transaccion',             component: DetalleFormComponent, canActivate: [authGuard] },
      { path: 'detalle-transaccion/page/:page',                             component: DetalleTransaccionComponent, canActivate: [authGuard]},
 
      // Direcciones
      { path: 'direcciones',                                        component: DireccionComponent, canActivate: [authGuard] },
      { path: 'direcciones/insertar-direccion',                     component: DireccionFormComponent, canActivate: [authGuard] },
      { path: 'direcciones/actualizar-direccion/:idDireccion',      component: DireccionFormComponent, canActivate: [authGuard] },
      { path: 'direcciones/page/:page',                             component: DireccionComponent, canActivate: [authGuard]},

      // Inscripciones Actividades
      { path: 'inscripciones-actividades',                                      component: InscripcionesActividadComponent, canActivate: [authGuard] },
      { path: 'inscripciones-actividades/registrar-inscripcion',                component: InscripcionesActividadFormComponent, canActivate: [authGuard] },
      { path: 'inscripciones-actividades/editar-inscripcion/:idInscripcion',    component: InscripcionesActividadFormComponent, canActivate: [authGuard] },
      { path: 'inscripciones-actividades/page/:page',                           component: InscripcionesActividadComponent, canActivate: [authGuard]},
 
      // Lugares Eventos
      { path: 'lugares-eventos',                                        component: LugarEventoComponent, canActivate: [authGuard] },
      { path: 'lugares-eventos/insertar-lugar-evento',                  component: LugarEventoFormComponent, canActivate: [authGuard] },
      { path: 'lugares-eventos/actualizar-lugar-evento/:idLugarEvento', component: LugarEventoFormComponent, canActivate: [authGuard] },
      { path: 'lugares-eventos/page/:page',                             component: LugarEventoComponent, canActivate: [authGuard]},
 
      // Módulo Reporte
      { path: 'modulo-reporte',                                     component: ModuloReporteComponent, canActivate: [authGuard] },
      { path: 'modulo-reporte/registrar-modulo-reporte',            component: ModuloReporteFormComponent, canActivate: [authGuard] },
      { path: 'modulo-reporte/editar-modulo-reporte/:idModulo',     component: ModuloReporteFormComponent, canActivate: [authGuard] },
      { path: 'modulo-reporte/page/:page',                          component: ModuloReporteComponent, canActivate: [authGuard]},
      // Movimientos Ahorro
      { path: 'movimientos-ahorro',                                         component: MovimientosAhorroComponent, canActivate: [authGuard] },
      { path: 'movimientos-ahorro/registrar-movimiento-ahorro',             component: MovimientosAhorroFormComponent, canActivate: [authGuard] },
      { path: 'movimientos-ahorro/editar-movimiento-ahorro/:idMovimiento',  component: MovimientosAhorroFormComponent, canActivate: [authGuard] },
      { path: 'movimientos-ahorro/page/:page',                              component: MovimientosAhorroComponent, canActivate: [authGuard]},
 
      // Pagos Préstamos
      { path: 'pagos-prestamos',                            component: PagosPrestamosComponent, canActivate: [authGuard] },
      { path: 'pagos-prestamos/registrar-pago-prestamo',    component: PagoFormComponent, canActivate: [authGuard] },
      { path: 'pagos-prestamos/editar-pago-prestamo/:idPago',       component: PagoFormComponent, canActivate: [authGuard] },
      { path: 'pagos-prestamos/page/:page',                 component: PagosPrestamosComponent, canActivate: [authGuard]},
 
      // Puestos Empresas
      { path: 'puestos-empresas',                                         component: PuestoEmpresaComponent, canActivate: [authGuard] },
      { path: 'puestos-empresas/registrar-puesto-empresa',                component: PuestoEmpresaFormComponent, canActivate: [authGuard] },
      { path: 'puestos-empresas/editar-puesto-empresa/:idPuestoEmpresa',  component: PuestoEmpresaFormComponent, canActivate: [authGuard] },
      { path: 'puestos-empresas/page/:page',                              component: PuestoEmpresaComponent, canActivate: [authGuard]},
 
      // Resultados Reportes
      { path: 'resultados-reportes',                                        component: ResultadoReporteComponent, canActivate: [authGuard] },
      { path: 'resultados-reportes/registrar-resultado-reporte',            component: ResultadoReporteFormComponent, canActivate: [authGuard] },
      { path: 'resultados-reportes/editar-resultado-reporte/:idResultado',  component: ResultadoReporteFormComponent, canActivate: [authGuard] },
      { path: 'resultados-reportes/page/:page',                             component: ResultadoReporteComponent, canActivate: [authGuard]},
 
      // Roles
      { path: 'roles',                      component: RolComponent, canActivate: [authGuard] },
      { path: 'roles/registrar-rol',        component: RolFormComponent, canActivate: [authGuard] },
      { path: 'roles/editar-rol/:idRol',    component: RolFormComponent, canActivate: [authGuard] },
      { path: 'roles/page/:page',           component: RolComponent, canActivate: [authGuard]},
 
      // Teléfonos
      { path: 'telefonos',                                  component: TelefonoComponent, canActivate: [authGuard] },
      { path: 'telefonos/registrar-telefono',               component: TelefonoFormComponent, canActivate: [authGuard] },
      { path: 'telefonos/editar-telefono/:idTelefono',      component: TelefonoFormComponent, canActivate: [authGuard] },
      { path: 'telefonos/page/:page',                         component: TelefonoComponent, canActivate: [authGuard] },


      // Tipos de Ahorro
      { path: 'tipos-ahorros',                                      component: TipoAhorroComponent, canActivate: [authGuard] },
      { path: 'tipos-ahorros/registrar-tipo-ahorro',                component: TipoAhorroFormComponent, canActivate: [authGuard] },
      { path: 'tipos-ahorros/editar-tipo-ahorro/:idTipoAhorro',     component: TipoAhorroFormComponent, canActivate: [authGuard] },
      { path: 'tipos-ahorros/page/:page',                           component: TipoAhorroComponent, canActivate: [authGuard]},
 
      // Tipos de Reporte
      { path: 'tipos-reportes',                                         component: TipoReporteComponent, canActivate: [authGuard] },
      { path: 'tipos-reportes/registrar-tipo-reporte',                  component: TipoReporteFormComponent, canActivate: [authGuard] },
      { path: 'tipos-reportes/editar-tipo-reporte/:idTipoReporte',      component: TipoReporteFormComponent, canActivate: [authGuard] },
      { path: 'tipos-reportes/page/:page',                              component: TipoReporteComponent, canActivate: [authGuard]},
 
      // Tipos de Transacciones
      { path: 'tipos-transacciones',                                            component: TipoTransaccionComponent, canActivate: [authGuard] },
      { path: 'tipos-transacciones/registrar-tipo-transaccion',                 component: TipoTransaccionFormComponent, canActivate: [authGuard] },
      { path: 'tipos-transacciones/editar-tipo-transaccion/:idTipoTransaccion', component: TipoTransaccionFormComponent, canActivate: [authGuard] },
      { path: 'tipos-transacciones/page/:page',                                 component: TipoTransaccionComponent, canActivate: [authGuard]}
    ]
  },
 
  // Comodín
  { path: '**', redirectTo: 'login' }
];