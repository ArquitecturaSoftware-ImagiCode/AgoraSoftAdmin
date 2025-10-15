export interface Modulo {
  id: number;
  nombre: string;
  descripcion: string;
  icono: string;
  precioMensual: number;
  activo: boolean;
  fechaCreacion: string;
  fechaActualizacion?: string;
  creadoPorId?: number;
  creadoPorNombre?: string;
  cantidadPlazasContratadas: number;
}

export interface CrearModuloDTO {
  nombre: string;
  descripcion: string;
  icono: string;
  precioMensual: number;
}
