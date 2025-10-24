export class Subscription {
  constructor(
    public id?: number,
    public stripeSubscriptionId?: string,
    public stripeCustomerId?: string,
    public priceId?: string,
    public status?: string,
    public currentPeriodStart?: Date,
    public currentPeriodEnd?: Date,
    public canceledAt?: Date,
    public organizationId?: string,
    public createdAt?: Date,
    public updatedAt?: Date
  ) {}
}
