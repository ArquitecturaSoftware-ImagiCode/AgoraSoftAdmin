import { Component, OnInit } from '@angular/core';
import { Empleado, EmpleadoService } from '../../../services/empleado.service';

@Component({
  selector: 'app-lista-empleados',
  templateUrl: './lista-empleados.component.html',
  styleUrls: ['./lista-empleados.component.css']
})
export class ListaEmpleadosComponent implements OnInit {
  empleados: Empleado[] = [];

  constructor(private empleadoService: EmpleadoService) {}

  ngOnInit() {
    this.empleadoService.getEmpleados().subscribe(data => this.empleados = data);
  }
}
