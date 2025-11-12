export class Usuario{

  constructor(
    public id?: string,
    public nombre?: string,
    public apellido?: string,
    public correo?: string,
    public rol?: string,
    public organizacion?: string,
    public passwordHash?: string,
    public activo?: boolean,
    public createdAt?: Date,
    public plaza?: any
  ){}
}
