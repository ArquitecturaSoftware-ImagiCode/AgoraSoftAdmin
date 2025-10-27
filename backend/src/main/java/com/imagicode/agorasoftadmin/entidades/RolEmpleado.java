package com.imagicode.agorasoftadmin.entidades;

public enum RolEmpleado {
    SUPER_ADMIN("Super Administrador", new String[] {
            "FULL_ACCESS",
            "MANAGE_EMPLOYEES",
            "MANAGE_PLAZAS",
            "MANAGE_MODULES",
            "VIEW_REPORTS",
            "SYSTEM_CONFIG"
    }),

    ADMIN("Administrador", new String[] {
            "MANAGE_PLAZAS",
            "APPROVE_PLAZAS",
            "VIEW_EMPLOYEES",
            "VIEW_REPORTS"
    }),

    SOPORTE("Soporte", new String[] {
            "VIEW_PLAZAS",
            "SEND_NOTIFICATIONS",
            "VIEW_BASIC_REPORTS"
    });

    private final String descripcion;
    private final String[] permisos;

    RolEmpleado(String descripcion, String[] permisos) {
        this.descripcion = descripcion;
        this.permisos = permisos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String[] getPermisos() {
        return permisos;
    }

    public boolean tienePermiso(String permiso) {
        for (String p : permisos) {
            if (p.equals(permiso) || p.equals("FULL_ACCESS")) {
                return true;
            }
        }
        return false;
    }
}
