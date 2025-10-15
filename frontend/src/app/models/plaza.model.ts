export interface Plaza {
  id: number;
  nombre: string;
  rut: string;
  direccion: string;
  telefono: string;
  emailContacto: string;
  representanteLegal: string;
  fechaRegistro: string;
  estado: EstadoPlaza;
  estadoDescripcion: string;
  clerkUserId?: string;

  // Información de aprobación
  aprobadoPorId?: number;
  aprobadoPorNombre?: string;
  fechaAprobacion?: string;

  // Información de rechazo
  rechazadoPorId?: number;
  rechazadoPorNombre?: string;
  fechaRechazo?: string;
  motivoRechazo?: string;

  // Información de suspensión
  suspendidoPorId?: number;
  suspendidoPorNombre?: string;
  fechaSuspension?: string;
  motivoSuspension?: string;

  // Estadísticas
  cantidadModulosActivos: number;
  planSuscripcion?: string;
  estadoPago?: string;

  fechaActualizacion?: string;
}

export enum EstadoPlaza {
  PENDIENTE = 'PENDIENTE',
  ACTIVA = 'ACTIVA',
  SUSPENDIDA = 'SUSPENDIDA',
  INACTIVA = 'INACTIVA',
  RECHAZADA = 'RECHAZADA'
}

export interface CrearPlazaDTO {
  nombre: string;
  rut: string;
  direccion: string;
  telefono: string;
  emailContacto: string;
  representanteLegal: string;
}

export interface AprobarPlazaDTO {
  planInicial: string;
  montoMensual: number;
  metodoPago?: string;
}

export interface RechazarPlazaDTO {
  motivo: string;
}

export interface SuspenderPlazaDTO {
  motivo: string;
}
