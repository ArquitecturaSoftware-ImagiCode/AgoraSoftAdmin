import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UsuarioService } from '../../../services/usuario.service';
import { OrganizationService, Organization } from '../../../services/organization.service';
import { SubscriptionService } from '../../../services/subscription.service';
import { AuthService } from '../../../services/auth.service';
import { Usuario } from '../../../models/Usuario';
import { Subscription } from '../../../models/Subscription';
import { firstValueFrom, catchError } from 'rxjs';

type Plaza = {
  id: number;
  nombre: string;
  estadoPago: 'pendiente' | 'pagado';
  monto: number;
  fechaLimite: string;
  organizationId?: string;
  subscription?: Subscription;
};

type UsuarioEmpresa = {
  id: string;
  nombre: string;
  correo: string;
  rol: string;
  apellido: string;
  organizacion: string;
};

@Component({
  selector: 'app-gestion-pagos',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './gestion-pagos.html',
  styleUrl: './gestion-pagos.css',
})
export class GestionPagosPage implements OnInit {
  plazas: Plaza[] = [];
  organizaciones: Organization[] = [];
  suscripciones: Subscription[] = [];
  usuarios: UsuarioEmpresa[] = [];
  token: string = '';
  
  cargandoUsuarios: boolean = true;
  cargandoPlazas: boolean = true;

  // Constante para el precio estándar: $40 USD = ~160,000 COP (aproximadamente 4,000 COP por USD)
  private readonly PRECIO_STANDAR_USD = 40;
  private readonly TASA_CAMBIO_USD_COP = 4000; // Tasa aproximada USD a COP
  private readonly PRECIO_STANDAR_COP = this.PRECIO_STANDAR_USD * this.TASA_CAMBIO_USD_COP;

  constructor(
    private usuarioService: UsuarioService,
    private organizationService: OrganizationService,
    private subscriptionService: SubscriptionService,
    private authService: AuthService
  ) {}

  async ngOnInit() {
    // Obtener token de autenticación
    if (!this.authService.isSignedIn()) {
      console.warn('Usuario no autenticado');
      return;
    }
    
    this.token = (await this.authService.getToken()) || '';
    if (!this.token) {
      console.error('No se obtuvo un token válido.');
      return;
    }
    
    this.cargarUsuariosContratistas();
    await this.cargarPlazas();
  }

  get plazasPendientes(): Plaza[] {
    return this.plazas.filter((p) => p.estadoPago === 'pendiente');
  }

  get plazasPagadas(): Plaza[] {
    return this.plazas.filter((p) => p.estadoPago === 'pagado');
  }

  cargarUsuariosContratistas() {
    this.cargandoUsuarios = true;
    console.log('Cargando usuarios contratistas...');
    this.usuarioService.getUsuariosPorOrganizacion('contratista').subscribe({
      next: (usuarios: Usuario[]) => {
        console.log('Usuarios recibidos:', usuarios);
        this.usuarios = usuarios.map(usuario => ({
          id: usuario.id || '',
          nombre: usuario.nombre || '',
          correo: usuario.correo || '',
          rol: usuario.rol || '',
          apellido: usuario.apellido || '',
          organizacion: usuario.organizacion || ''
        }));
        console.log('Usuarios mapeados:', this.usuarios);
        this.cargandoUsuarios = false;
      },
      error: (error: any) => {
        console.error('Error al cargar usuarios contratistas:', error);
        this.cargandoUsuarios = false;
      }
    });
  }

  async cargarPlazas() {
    this.cargandoPlazas = true;
    
    try {
      console.log('[GESTION-PAGOS] Iniciando carga de plazas...');
      console.log('[GESTION-PAGOS] Token:', this.token ? 'presente' : 'ausente');
      
      // Cargar organizaciones
      console.log('[GESTION-PAGOS] Llamando a getAll con token...');
      
      // Usar catchError para capturar errores de manera más detallada
      const organizacionesObs = this.organizationService.getAll(this.token);
      
      this.organizaciones = await firstValueFrom(
        organizacionesObs.pipe(
          catchError(error => {
            console.error('[GESTION-PAGOS] ❌ Error en la petición HTTP:', error);
            console.error('[GESTION-PAGOS] Status:', error.status);
            console.error('[GESTION-PAGOS] Error message:', error.message);
            console.error('[GESTION-PAGOS] Error body:', error.error);
            throw error;
          })
        )
      );
      
      console.log('[GESTION-PAGOS] Organizaciones recibidas:', this.organizaciones);
      console.log('[GESTION-PAGOS] Cantidad de organizaciones:', this.organizaciones?.length || 0);
      
      if (!this.organizaciones || this.organizaciones.length === 0) {
        console.warn('[GESTION-PAGOS] ⚠️ No se recibieron organizaciones');
        this.cargandoPlazas = false;
        return;
      }
      
      // Cargar suscripciones
      console.log('[GESTION-PAGOS] Cargando suscripciones...');
      this.subscriptionService.getSubscriptions().subscribe({
        next: (suscripciones: Subscription[]) => {
          console.log('[GESTION-PAGOS] Suscripciones recibidas:', suscripciones);
          console.log('[GESTION-PAGOS] Cantidad de suscripciones:', suscripciones?.length || 0);
          
          this.suscripciones = suscripciones || [];
          
          // Generar plazas basadas en organizaciones y suscripciones
          this.generarPlazas();
          this.cargandoPlazas = false;
        },
        error: (error: any) => {
          console.error('[GESTION-PAGOS] Error al cargar suscripciones:', error);
          // Continuar aunque falle la carga de suscripciones
          this.suscripciones = [];
          this.generarPlazas();
          this.cargandoPlazas = false;
        }
      });
    } catch (error: any) {
      console.error('[GESTION-PAGOS] Error al cargar organizaciones:', error);
      console.error('[GESTION-PAGOS] Error details:', error.message);
      console.error('[GESTION-PAGOS] Error stack:', error.stack);
      this.cargandoPlazas = false;
    }
  }

  generarPlazas() {
    console.log('=== GENERANDO PLAZAS DESDE ORGANIZACIONES ===');
    console.log('Total organizaciones:', this.organizaciones.length);
    console.log('Total suscripciones:', this.suscripciones.length);
    
    // Generar plazas desde las organizaciones
    this.plazas = this.organizaciones.map((org, index) => {
      // Buscar si esta organización tiene una suscripción (sin importar el estado)
      const suscripcion = this.suscripciones.find(sub => {
        return sub.organizationId === org.clerkOrgId;
      });

      // Lógica simple: Si tiene suscripción → pagado, si no → pendiente
      const estadoPago: 'pendiente' | 'pagado' = suscripcion ? 'pagado' : 'pendiente';
      
      const monto = this.PRECIO_STANDAR_COP;
      const fechaLimite = new Date();
      fechaLimite.setMonth(fechaLimite.getMonth() + 1);

      const plaza = {
        id: org.id || index + 1,
        nombre: org.nombre || `Organización ${org.id}`,
        estadoPago,
        monto,
        fechaLimite: fechaLimite.toISOString().split('T')[0],
        organizationId: org.clerkOrgId,
        subscription: suscripcion
      };

      console.log(`Plaza ${index + 1}: ${org.nombre} - Estado: ${estadoPago} ${suscripcion ? '(tiene suscripción)' : '(sin suscripción)'}`);
      return plaza;
    });

    console.log('=== RESULTADO FINAL ===');
    console.log('Total plazas:', this.plazas.length);
    console.log('Plazas pagadas:', this.plazasPagadas.length);
    console.log('Plazas pendientes:', this.plazasPendientes.length);
  }
}


