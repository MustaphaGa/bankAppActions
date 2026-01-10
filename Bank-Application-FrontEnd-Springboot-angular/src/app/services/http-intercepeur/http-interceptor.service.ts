// http-interceptor.service.ts
import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

export const httpInterceptor: HttpInterceptorFn = (req, next) => {
  const platformId = inject(PLATFORM_ID);

  if (isPlatformBrowser(platformId)) {
    const token = localStorage.getItem('token');

    const isValidToken =
      token && token !== 'null' && token !== 'undefined' && token.trim() !== '';

    const isPublicEndpoint =
      req.url.includes('/auth/login') || req.url.includes('/auth/register');

    if (isValidToken && !isPublicEndpoint) {
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      return next(authReq);
    }
  }

  return next(req);
};
