import { Injectable, EventEmitter, Output, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { APP_CONFIG } from '../../../app.config';
import { AppConfig } from '../../../models/app-config.interface';

@Injectable({
	providedIn: 'root'
})
export class PromoService {
	private baseURL: string;
	public appState: string;
	public appId: string;
	@Output() stateSet: EventEmitter<string> = new EventEmitter();
	@Output() idSet: EventEmitter<string> = new EventEmitter();

	constructor(private http: HttpClient, @Inject(APP_CONFIG) private appConfig: AppConfig) {
		this.baseURL = appConfig.cmsUrl;
	}

	static handleErrors(error: HttpErrorResponse) {
		const errMsgs = [];
		if(error.error instanceof Array) {
			error.error.forEach(e => {
				if (e.message) {
					errMsgs.push(e.message);
				}
			});
		} else if (error.error && error.error.message) {
			errMsgs.push(error.error.message);
		} else if (error.message) {
			errMsgs.push(error.message);
		} else {
			errMsgs.push('Something bad happened. Error not recognized.');
		}
		return throwError(errMsgs);
	}

	setAppState(appState: string) {
		// console.log('PromoService.setAppState(' + appState + ')');
		this.appState = appState;
		this.stateSet.emit(this.appState);
	}

	setAppId(appId: string) {
		// console.log('PromoService.setAppId(' + appId + ')');
		this.appId = appId;
		this.idSet.emit(this.appId);
	}

	getPromos(): Observable<Array<any>> {
		const escAppId = this.appId.replace('-', '%5C-');
		// tslint:disable-next-line:max-line-length
		const url = this.baseURL + '/api/content/render/false/query/+contentType:Apppromo%20+(conhost:8a7d5e23-da1e-420a-b4f0-471e7da8ea2d%20conhost:SYSTEM_HOST)%20+languageId:1%20+deleted:false%20+Apppromo.application:' + escAppId + '%20+Apppromo.placement:main%20+working:true/orderby/Apppromo.application';
		// console.log('PromoService.getPromos(): ' + url);
		return this.http.get<any>(url, {
			headers: new HttpHeaders({
				'X-Skip-Interceptor': 'Yes',
				'Content-Type': 'application/json'
			})
		})
		.pipe(
			catchError(PromoService.handleErrors)
		);
	}
	getLeftPromos(): Observable<Array<any>> {
		// tslint:disable-next-line:max-line-length
		const url = this.baseURL + '/api/content/render/false/query/+contentType:Apppromo%20+(conhost:8a7d5e23-da1e-420a-b4f0-471e7da8ea2d%20conhost:SYSTEM_HOST)%20+languageId:1%20+deleted:false%20+Apppromo.application:("' + this.appId + '"%20%7C%7C%20"all")%20+Apppromo.placement:left%20+working:true/orderby/modDate%20desc';
		//console.log('PromoService.getLeftPromos(): ' + url);
		return this.http.get<any>(url, {
			headers: new HttpHeaders({
				'X-Skip-Interceptor': 'Yes',
				'Content-Type': 'application/json'
			})
		})
		.pipe(
			catchError(PromoService.handleErrors)
		);
	}
}
