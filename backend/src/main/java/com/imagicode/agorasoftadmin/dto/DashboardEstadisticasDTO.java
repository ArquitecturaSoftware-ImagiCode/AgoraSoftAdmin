package com.imagicode.agorasoftadmin.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DashboardEstadisticasDTO {

    // Estadísticas de plazas
    private long totalPlazas;
    private long plazasActivas;
    private long plazasPendientes;
    private long plazasSuspendidas;
    private long plazasInactivas;

    // Estadísticas de empleados
    private long totalEmpleados;
    private long empleadosActivos;

    // Estadísticas financieras
    private BigDecimal ingresosMensuales;
    private BigDecimal ingresosMesActual;

    // Módulos
    private long totalModulos;
    private long modulosActivos;

    // Top módulos contratados
    private List<ModuloEstadisticaDTO> topModulosContratados;

    // Plazas recientes
    private List<PlazaDTO> ultimasPlazasRegistradas;

    // Plazas con pagos vencidos
    private List<PlazaDTO> plazasConPagosVencidos;

    // Distribución por estado
    private Map<String, Long> distribucionPorEstado;

    // Constructores
    public DashboardEstadisticasDTO() {
    }

    // Getters y Setters
    public long getTotalPlazas() {
        return totalPlazas;
    }

    public void setTotalPlazas(long totalPlazas) {
        this.totalPlazas = totalPlazas;
    }

    public long getPlazasActivas() {
        return plazasActivas;
    }

    public void setPlazasActivas(long plazasActivas) {
        this.plazasActivas = plazasActivas;
    }

    public long getPlazasPendientes() {
        return plazasPendientes;
    }

    public void setPlazasPendientes(long plazasPendientes) {
        this.plazasPendientes = plazasPendientes;
    }

    public long getPlazasSuspendidas() {
        return plazasSuspendidas;
    }

    public void setPlazasSuspendidas(long plazasSuspendidas) {
        this.plazasSuspendidas = plazasSuspendidas;
    }

    public long getPlazasInactivas() {
        return plazasInactivas;
    }

    public void setPlazasInactivas(long plazasInactivas) {
        this.plazasInactivas = plazasInactivas;
    }

    public long getTotalEmpleados() {
        return totalEmpleados;
    }

    public void setTotalEmpleados(long totalEmpleados) {
        this.totalEmpleados = totalEmpleados;
    }

    public long getEmpleadosActivos() {
        return empleadosActivos;
    }

    public void setEmpleadosActivos(long empleadosActivos) {
        this.empleadosActivos = empleadosActivos;
    }

    public BigDecimal getIngresosMensuales() {
        return ingresosMensuales;
    }

    public void setIngresosMensuales(BigDecimal ingresosMensuales) {
        this.ingresosMensuales = ingresosMensuales;
    }

    public BigDecimal getIngresosMesActual() {
        return ingresosMesActual;
    }

    public void setIngresosMesActual(BigDecimal ingresosMesActual) {
        this.ingresosMesActual = ingresosMesActual;
    }

    public long getTotalModulos() {
        return totalModulos;
    }

    public void setTotalModulos(long totalModulos) {
        this.totalModulos = totalModulos;
    }

    public long getModulosActivos() {
        return modulosActivos;
    }

    public void setModulosActivos(long modulosActivos) {
        this.modulosActivos = modulosActivos;
    }

    public List<ModuloEstadisticaDTO> getTopModulosContratados() {
        return topModulosContratados;
    }

    public void setTopModulosContratados(List<ModuloEstadisticaDTO> topModulosContratados) {
        this.topModulosContratados = topModulosContratados;
    }

    public List<PlazaDTO> getUltimasPlazasRegistradas() {
        return ultimasPlazasRegistradas;
    }

    public void setUltimasPlazasRegistradas(List<PlazaDTO> ultimasPlazasRegistradas) {
        this.ultimasPlazasRegistradas = ultimasPlazasRegistradas;
    }

    public List<PlazaDTO> getPlazasConPagosVencidos() {
        return plazasConPagosVencidos;
    }

    public void setPlazasConPagosVencidos(List<PlazaDTO> plazasConPagosVencidos) {
        this.plazasConPagosVencidos = plazasConPagosVencidos;
    }

    public Map<String, Long> getDistribucionPorEstado() {
        return distribucionPorEstado;
    }

    public void setDistribucionPorEstado(Map<String, Long> distribucionPorEstado) {
        this.distribucionPorEstado = distribucionPorEstado;
    }
}