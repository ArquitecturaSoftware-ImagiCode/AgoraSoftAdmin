export class Organization {
  constructor(
    public id?: number,
    public clerkOrgId?: string,
    public name?: string,
    public ownerId?: number,
    public isActive?: boolean,
    public createdAt?: Date,
    public updatedAt?: Date
  ) {}
}
