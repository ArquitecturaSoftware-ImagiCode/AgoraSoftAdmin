export interface Plaza {
  id: number;
  nombre: string;
  rut?: string;
  direccion?: string;
  telefono?: string;
  emailContacto?: string;
  representanteLegal?: string;
  fechaRegistro?: string;
  fechaActualizacion?: string;
  estado?: EstadoPlaza | string;
  estadoDescripcion?: string;

  // Aprobación / rechazo / suspensión
  aprobadoPorId?: number;
  aprobadoPorNombre?: string;
  fechaAprobacion?: string;
  rechazadoPorId?: number;
  rechazadoPorNombre?: string;
  fechaRechazo?: string;
  motivoRechazo?: string;
  suspendidoPorId?: number;
  suspendidoPorNombre?: string;
  fechaSuspension?: string;
  motivoSuspension?: string;

  // Suscripción / pagos
  planSuscripcion?: string;
  estadoPago?: string;

  // Estadísticas
  cantidadModulosActivos?: number;
  cantidadUsuarios?: number;
}

export enum EstadoPlaza {
  PENDIENTE = 'PENDIENTE',
  ACTIVA = 'ACTIVA',
  SUSPENDIDA = 'SUSPENDIDA',
  INACTIVA = 'INACTIVA',
  RECHAZADA = 'RECHAZADA',
}

export interface CrearPlazaDTO {
  nombre: string;
  rut?: string;
  direccion?: string;
  telefono?: string;
  emailContacto?: string;
  representanteLegal?: string;
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
