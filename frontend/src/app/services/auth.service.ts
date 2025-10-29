import { Injectable } from '@angular/core';
import { Clerk } from '@clerk/clerk-js';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private clerk: Clerk;
  private signedIn$ = new BehaviorSubject<boolean>(false);

  constructor() {
    this.clerk = new Clerk('pk_test_YWNjZXB0ZWQtdGVybWl0ZS05MS5jbGVyay5hY2NvdW50cy5kZXYk');
    this.initializeClerk();
  }

  private async initializeClerk() {
    await this.clerk.load();
    this.signedIn$.next(this.clerk.isSignedIn);

    this.clerk.addListener(({ user }) => {
      this.signedIn$.next(!!user);
    });
  }

  getSignedInStatus(): Observable<boolean> {
    return this.signedIn$.asObservable();
  }

  async signUp(
    firstName: string,
    lastName: string,
    email: string,
    password: string,
    role: string,
    plaza: string
  ) {
    return this.clerk?.client?.signUp.create({
      firstName,
      lastName,
      emailAddress: email,
      password,
      unsafeMetadata: { role, plaza },
    });
  }

  async sendVerification() {
    return this.clerk?.client?.signUp.prepareEmailAddressVerification();
  }

  async verifyEmail(code: string) {
    const signUpAttempt = await this.clerk?.client?.signUp.attemptEmailAddressVerification({ code });
    if (signUpAttempt?.status === 'complete') {
      await this.clerk.setActive({ session: signUpAttempt.createdSessionId });
      this.signedIn$.next(true);
    }
    return signUpAttempt;
  }

  async getToken(): Promise<string | null> {
    try {
      const session = await this.clerk?.session;
      if (!session) return null;
      const token = await session.getToken();
      console.log('[AuthService] Token obtenido:', token);
      return token ?? null;
    } catch (error) {
      console.error('[AuthService] Error obteniendo token:', error);
      return null;
    }
  }

  /** 🔹 NUEVO: obtener el ID del usuario actual */
  async getUserId(): Promise<string | null> {
    try {
      const user = await this.clerk.user;
      return user?.id || null;
    } catch (error) {
      console.error('[AuthService] Error obteniendo userId:', error);
      return null;
    }
  }

  async signIn(email: string, password: string) {
    const signInAttempt = await this.clerk?.client?.signIn.create({
      identifier: email,
      password,
    });

    if (signInAttempt?.status === 'complete') {
      await this.clerk.setActive({ session: signInAttempt.createdSessionId });
      this.signedIn$.next(true);
    }
    return signInAttempt;
  }

  async signOut() {
    await this.clerk.signOut();
    this.signedIn$.next(false);
  }

  isSignedIn(): boolean {
    return this.clerk.isSignedIn;
  }

  async getUserRole(): Promise<string | undefined> {
    const user = await this.clerk.user;
    return user?.unsafeMetadata?.['role'] as string | undefined;
  }

  getClerkInstance(): Clerk {
    return this.clerk;
  }
}
