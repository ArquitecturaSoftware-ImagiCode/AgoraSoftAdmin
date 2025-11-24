package com.imagicode.agorasoftadmin;

import com.imagicode.agorasoftadmin.dto.OrganizationModuloDTO;
import com.imagicode.agorasoftadmin.entidades.Modulo;
import com.imagicode.agorasoftadmin.entidades.Organization;
import com.imagicode.agorasoftadmin.entidades.OrganizationModulo;
import com.imagicode.agorasoftadmin.repositorios.ModuloRepository;
import com.imagicode.agorasoftadmin.repositorios.OrganizationModuloRepository;
import com.imagicode.agorasoftadmin.repositorios.OrganizationRepository;
import com.imagicode.agorasoftadmin.servicios.OrganizationModuloService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizationModuloServiceTest {

    @Mock private OrganizationModuloRepository organizationModuloRepository;
    @Mock private OrganizationRepository organizationRepository;
    @Mock private ModuloRepository moduloRepository;

    @InjectMocks private OrganizationModuloService service;

    private Organization org;
    private Modulo mod;

    @BeforeEach
    void setup() {
        org = new Organization();
        org.setId(1L);
        org.setNombre("Org1");

        mod = new Modulo();
        mod.setId(2L);
        mod.setNombre("ModuloX");
    }

    @Test
    void asignarModuloAOrganization_CuandoNoExisteRelacion_DeberiaCrear() {
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(org));
        when(moduloRepository.findById(2L)).thenReturn(Optional.of(mod));
        when(organizationModuloRepository.findByOrganizationIdAndModuloId(1L, 2L))
                .thenReturn(Optional.empty());

        OrganizationModulo created = new OrganizationModulo(org, mod, true, 10L, "Admin");
        created.setId(100L);

        when(organizationModuloRepository.save(any(OrganizationModulo.class))).thenReturn(created);

        OrganizationModuloDTO dto = service.asignarModuloAOrganization(1L, 2L, 10L, "Admin");

        assertNotNull(dto);
        assertEquals(100L, dto.getId());
        assertEquals(1L, dto.getOrganizationId());
        assertEquals(2L, dto.getModuloId());

        verify(organizationRepository).findById(1L);
        verify(moduloRepository).findById(2L);
        verify(organizationModuloRepository).findByOrganizationIdAndModuloId(1L, 2L);
        verify(organizationModuloRepository).save(any(OrganizationModulo.class));
    }

    @Test
    void asignarModuloAOrganization_CuandoExisteRelacion_DeberiaActivarYActualizarAdmin() {
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(org));
        when(moduloRepository.findById(2L)).thenReturn(Optional.of(mod));

        OrganizationModulo existing = new OrganizationModulo(org, mod, false, 5L, "Old");
        existing.setId(55L);

        when(organizationModuloRepository.findByOrganizationIdAndModuloId(1L, 2L))
                .thenReturn(Optional.of(existing));

        OrganizationModulo updated = new OrganizationModulo(org, mod, true, 10L, "Admin");
        updated.setId(55L);

        when(organizationModuloRepository.save(existing)).thenReturn(updated);

        OrganizationModuloDTO dto = service.asignarModuloAOrganization(1L, 2L, 10L, "Admin");

        assertNotNull(dto);
        assertEquals(55L, dto.getId());
        assertTrue(dto.getActivo());
        assertEquals("Admin", dto.getActivadoPorAdminNombre());

        verify(organizationModuloRepository).findByOrganizationIdAndModuloId(1L, 2L);
        verify(organizationModuloRepository).save(existing);
    }

    @Test
    void desactivarModuloDeOrganization_DeberiaMarcarInactivo() {
        OrganizationModulo existing = new OrganizationModulo(org, mod, true, 5L, "AdminOld");
        existing.setId(200L);

        when(organizationModuloRepository.findByOrganizationIdAndModuloId(1L, 2L))
                .thenReturn(Optional.of(existing));

        when(organizationModuloRepository.save(existing)).thenReturn(existing);

        OrganizationModuloDTO dto = service.desactivarModuloDeOrganization(1L, 2L, 99L, "NewAdmin");

        assertNotNull(dto);
        assertFalse(dto.getActivo());
        assertEquals("NewAdmin", dto.getActivadoPorAdminNombre());

        verify(organizationModuloRepository).findByOrganizationIdAndModuloId(1L, 2L);
        verify(organizationModuloRepository).save(existing);
    }
}
