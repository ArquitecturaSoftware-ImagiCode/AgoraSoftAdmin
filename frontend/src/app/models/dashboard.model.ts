  import { Plaza } from './plaza.model';

export interface DashboardEstadisticas {
  // Estadísticas de plazas
  totalPlazas: number;
  plazasActivas: number;
  plazasPendientes: number;
  plazasSuspendidas: number;
  plazasInactivas: number;

  // Estadísticas de empleados
  totalEmpleados: number;
  empleadosActivos: number;

  // Estadísticas financieras
  ingresosMensuales: number;
  ingresosMesActual: number;

  // Módulos
  totalModulos: number;
  modulosActivos: number;

  // Top módulos contratados
  topModulosContratados: ModuloEstadistica[];

  // Plazas recientes
  ultimasPlazasRegistradas: Plaza[];

  // Plazas con pagos vencidos
  plazasConPagosVencidos: Plaza[];

  // Distribución por estado
  distribucionPorEstado: { [key: string]: number };
}

export interface ModuloEstadistica {
  moduloId: number;
  nombreModulo: string;
  icono: string;
  cantidadPlazas: number;
}
