import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (!req.headers.has('Content-Type')) {
      req = req.clone({
        setHeaders: { 'Content-Type': 'application/json' }
      });
    }

    if (req.headers.has('Remove-Content-Type')) {
      req = req.clone({
        headers: req.headers.delete('Content-Type').delete('Remove-Content-Type')
      });
    }

    if (req.headers.has('X-Skip-Interceptor')) {
      const headers = req.headers.delete('X-Skip-Interceptor');
      return next.handle(req.clone({ headers }));
    }

    const authToken = this.authService.getAuthToken();

    if (authToken) {
      const authReq = req.clone({
        setHeaders: {
          Authorization: 'Bearer ' + authToken.access_token
        }
      });
      return next.handle(authReq);
    } else {
      // if (!req.headers.has('Content-Type')) {
      //   const authReq = req.clone({
      //     setHeaders: { 'Content-Type': 'application/json' }
      //   });
      //   return next.handle(authReq);
      // }
      return next.handle(req);
    }
  }
}
