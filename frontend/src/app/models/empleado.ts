export interface Empleado {
  id: number;
  clerkUserId?: string;
  nombre: string;
  apellido: string;
  correo: string;
  rol: RolEmpleado | string;
  rolDescripcion?: string;
  departamento?: string;
  telefono?: string;
  activo?: boolean;
  fechaContratacion?: string;
  fechaCreacion?: string;
  fechaActualizacion?: string;
}

export enum RolEmpleado {
  SUPER_ADMIN = 'SUPER_ADMIN',
  ADMIN = 'ADMIN',
  SOPORTE = 'SOPORTE',
}

export interface CrearEmpleadoDTO {
  nombre: string;
  apellido: string;
  correo: string;
  rol: RolEmpleado | string;
  departamento?: string;
  telefono?: string;
}

export interface ActualizarEmpleadoDTO {
  nombre?: string;
  apellido?: string;
  rol?: RolEmpleado | string;
  departamento?: string;
  telefono?: string;
  activo?: boolean;
}
