import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    GuardResult,
    MaybeAsync,
    Router,
    RouterStateSnapshot,
} from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
    providedIn: 'root',
})
export class TokenGuardService implements CanActivate {
    constructor(private router: Router, @Inject(PLATFORM_ID) private platformId: Object) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): MaybeAsync<GuardResult> {
        if (isPlatformBrowser(this.platformId)) {
            const token = localStorage.getItem('token');
            console.log('guard token : ', token);
            if (!token) {
                this.router.navigate(['login']);
                return false;
            }
            const helper = new JwtHelperService();
            const tokenExpired = helper.isTokenExpired(token);
            if (tokenExpired) {
                localStorage.clear();
                this.router.navigate(['login']);
                return false;
            }
            return true;
        } else {
            this.router.navigate(['login']); // Or handle differently in non-browser env
            return false;
        }
    }
}