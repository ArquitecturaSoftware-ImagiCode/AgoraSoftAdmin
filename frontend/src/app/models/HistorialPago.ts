export class HistorialPago {
  constructor(
    public id?: number,
    public tipoPago?: string,
    public fechaPago?: Date,
    public monto?: number,
    public metodoPago?: string,
    public comprobante?: string,
    public estado?: string,
    public descripcion?: string,
    public empleadoId?: number,
    public organizacionId?: number,
    public registradoPorEmpleadoId?: number
  ) {}
}
