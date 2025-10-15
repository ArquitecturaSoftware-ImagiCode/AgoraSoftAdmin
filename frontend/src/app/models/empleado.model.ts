export interface Empleado {
  id: number;
  clerkUserId: string;
  nombre: string;
  apellido: string;
  correo: string;
  rol: RolEmpleado;
  rolDescripcion: string;
  departamento: string;
  fechaContratacion: string;
  activo: boolean;
  telefono?: string;
  fechaCreacion: string;
  fechaActualizacion?: string;
}

export enum RolEmpleado {
  SUPER_ADMIN = 'SUPER_ADMIN',
  ADMIN = 'ADMIN',
  SOPORTE = 'SOPORTE'
}

export interface CrearEmpleadoDTO {
  nombre: string;
  apellido: string;
  correo: string;
  rol: RolEmpleado;
  departamento: string;
  telefono?: string;
}

export interface ActualizarEmpleadoDTO {
  nombre?: string;
  apellido?: string;
  rol?: RolEmpleado;
  departamento?: string;
  telefono?: string;
  activo?: boolean;
}
