import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UsuarioService } from '../../../services/usuario.service';
import { OrganizationService } from '../../../services/organization.service';
import { SubscriptionService } from '../../../services/subscription.service';
import { Usuario } from '../../../models/Usuario';
import { Organization } from '../../../models/Organization';
import { Subscription } from '../../../models/Subscription';

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
  
  cargandoUsuarios: boolean = true;
  cargandoPlazas: boolean = true;

  constructor(
    private usuarioService: UsuarioService,
    private organizationService: OrganizationService,
    private subscriptionService: SubscriptionService
  ) {}

  ngOnInit() {
    this.cargarUsuariosContratistas();
    this.cargarPlazas();
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

  cargarPlazas() {
    this.cargandoPlazas = true;
    
    // Cargar organizaciones y suscripciones en paralelo
    this.organizationService.getOrganizations().subscribe({
      next: (organizaciones: Organization[]) => {
        this.organizaciones = organizaciones;
        console.log('Organizaciones cargadas:', organizaciones);
        
        // Cargar suscripciones después de cargar organizaciones
        this.subscriptionService.getSubscriptions().subscribe({
          next: (suscripciones: Subscription[]) => {
            this.suscripciones = suscripciones;
            console.log('Suscripciones cargadas:', suscripciones);
            
            // Generar plazas basadas en organizaciones y suscripciones
            this.generarPlazas();
            this.cargandoPlazas = false;
          },
          error: (error: any) => {
            console.error('Error al cargar suscripciones:', error);
            this.cargandoPlazas = false;
          }
        });
      },
      error: (error: any) => {
        console.error('Error al cargar organizaciones:', error);
        this.cargandoPlazas = false;
      }
    });
  }

  generarPlazas() {
    console.log('=== DEBUGGING PLAZAS ===');
    console.log('Organizaciones:', this.organizaciones);
    console.log('Suscripciones:', this.suscripciones);
    
    this.plazas = this.organizaciones.map((org, index) => {
      console.log(`\nProcesando organización ${index + 1}:`, org);
      console.log(`ClerkOrgId: "${org.clerkOrgId}"`);
      
      // Buscar suscripción para esta organización
      const suscripcion = this.suscripciones.find(sub => {
        console.log(`Comparando con suscripción organizationId: "${sub.organizationId}"`);
        const match = sub.organizationId === org.clerkOrgId;
        console.log(`¿Coincide? ${match}`);
        return match;
      });

      let estadoPago: 'pendiente' | 'pagado' = 'pendiente';
      let monto = 500000; // Monto base por defecto
      let fechaLimite = new Date();
      fechaLimite.setMonth(fechaLimite.getMonth() + 1);

      if (suscripcion) {
        console.log('Suscripción encontrada:', suscripcion);
        
        // Verificar si la suscripción es reciente (menos de un mes)
        const fechaCreacion = new Date(suscripcion.createdAt!);
        const unMesAtras = new Date();
        unMesAtras.setMonth(unMesAtras.getMonth() - 1);

        console.log(`Fecha creación suscripción: ${fechaCreacion}`);
        console.log(`Un mes atrás: ${unMesAtras}`);
        console.log(`Status: ${suscripcion.status}`);

        if (fechaCreacion > unMesAtras && (suscripcion.status === 'active' || suscripcion.status === 'pending')) {
          estadoPago = 'pagado';
          monto = 750000; // Monto para organizaciones con suscripción activa
          console.log('✅ Suscripción válida - marcando como pagado');
        } else {
          console.log('❌ Suscripción no válida o muy antigua');
        }
      } else {
        console.log('❌ No se encontró suscripción para esta organización');
      }

      const plaza = {
        id: org.id || index + 1,
        nombre: org.name || `Plaza ${org.id}`,
        estadoPago,
        monto,
        fechaLimite: fechaLimite.toISOString().split('T')[0],
        organizationId: org.clerkOrgId,
        subscription: suscripcion
      };

      console.log('Plaza generada:', plaza);
      return plaza;
    });

    console.log('=== RESULTADO FINAL ===');
    console.log('Total plazas:', this.plazas.length);
    console.log('Plazas pagadas:', this.plazas.filter(p => p.estadoPago === 'pagado').length);
    console.log('Plazas pendientes:', this.plazas.filter(p => p.estadoPago === 'pendiente').length);
    console.log('Plazas generadas:', this.plazas);
  }
}


