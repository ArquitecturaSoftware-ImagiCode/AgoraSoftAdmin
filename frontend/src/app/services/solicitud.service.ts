import { Injectable } from '@angular/core';
import axios from 'axios';
import { environment } from '../../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class SolicitudService {

  async listarSolicitudes(estado?: string, search?: string) {
    try {
      const params: any = {};
      if (estado) params.estado = estado;
      if (search) params.search = search;

      const response = await axios.get(`${environment.apiBaseUrl}/solicitudes`, { params });
      return response.data;
    } catch (error) {
      console.error('Error al listar solicitudes:', error);
      throw error;
    }
  }

  async actualizarEstado(id: number, nuevoEstado: string, quien: string, comentario?: string) {
    try {
      const response = await axios.put(`${environment.apiBaseUrl}/solicitudes/${id}/estado`, {
        nuevoEstado,
        quien,
        comentario
      });
      return response.data;
    } catch (error) {
      console.error('Error al actualizar estado:', error);
      throw error;
    }
  }
}
