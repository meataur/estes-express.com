import { Injectable, Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { APP_CONFIG } from '../../../app.config';
import { AppConfig } from '../../../models/app-config.interface';
import { MyEstesGlobalHandlers } from '../../../util/handlers/myestes-global-handlers';

@Injectable({
	providedIn: 'root'
})
export class CmsAlertService {
	private baseURL: string;

	constructor(private http: HttpClient, @Inject(APP_CONFIG) private appConfig: AppConfig) {
		this.baseURL = appConfig.cmsUrl;
	}

	getAlerts(appName: string): Observable<Array<any>> {
    // tslint:disable-next-line:max-line-length
    const url = `${this.baseURL}/api/content/render/false/type/json/query/+contentType:AlertType%20+(conhost:8a7d5e23-da1e-420a-b4f0-471e7da8ea2d%20conhost:SYSTEM_HOST)%20+AlertType.applications:*${appName}*%20+languageId:1%20+deleted:false%20+working:true/orderby/modDate%20desc`;
		return this.http.get<any>(url, {
			headers: new HttpHeaders({
				'X-Skip-Interceptor': '',
				'Content-Type': 'application/json'
			})
		})
		.pipe(
			catchError(MyEstesGlobalHandlers.handleErrors)
		);
	}
}
