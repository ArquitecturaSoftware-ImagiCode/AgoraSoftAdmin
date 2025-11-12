export interface DashboardEstadisticas {
  // Plazas
  totalPlazas: number;
  plazasActivas: number;
  plazasPendientes: number;
  plazasSuspendidas: number;
  plazasInactivas: number;

  // Empleados
  totalEmpleados: number;
  empleadosActivos: number;

  // Finanzas
  ingresosMensuales: number;
  ingresosMesActual: number;

  // Módulos
  totalModulos: number;
  modulosActivos: number;

  // Top módulos contratados
  topModulosContratados: ModuloEstadistica[];

  // Plazas recientes y alertas
  ultimasPlazasRegistradas: PlazaResumen[];
  plazasConPagosVencidos: PlazaResumen[];

  // Distribución por estado (opcional)
  distribucionPorEstado?: { [key: string]: number };
}

export interface ModuloEstadistica {
  moduloId: number;
  nombreModulo: string;
  icono?: string;
  cantidadPlazas: number;
}

export interface PlazaResumen {
  id: number;
  nombre: string;
  fechaRegistro?: string;
  estado?: string;
  estadoDescripcion?: string;
  planSuscripcion?: string;
}
