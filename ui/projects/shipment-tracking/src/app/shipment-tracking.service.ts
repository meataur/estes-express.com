import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { ShipmentTrackingRequest } from './models/shipment-tracking-request.model';
import { ShipmentTrackingResponse } from './models/shipment-tracking-response.model';
import { ImageRequest } from './models/image-request.model';
import { ImageResponse } from './models/image-response.model';
import { EmailStatusRequest } from './models/email-status-request.model';
import { EmailStatusResponse } from './models/email-status-response.model';
import { WRCertificate } from './models/wr-certificate.interface';
import { environment } from '../environments/environment';
import { ShipmentImageRequest } from './models/shipment-image-request.model';
import { ShipmentImageResponse } from './models/shipment-image-response.model';

@Injectable({
	providedIn: 'root'
})
export class ShipmentTrackingService {

	constructor(private httpClient: HttpClient) {}


	static handleErrors(error: HttpErrorResponse) {
		if (error.error instanceof ErrorEvent) {
			// console.error('An error occurred:', error.error.message);
		} else {
			// console.error(`Backend returned code ${error.status}, ` + `body was: ${error.error}`);
		}
		return throwError(error.message);
	}

	searchShipments(
		shipmentRequest: ShipmentTrackingRequest
	): Observable<ShipmentTrackingResponse[]> {
		const url = `${environment.serviceApiUrl}/myestes/ShipmentTracking/v1.0/search`;
		return this.httpClient
			.post<ShipmentTrackingResponse[]>(url, JSON.stringify(shipmentRequest))
			.pipe(catchError(ShipmentTrackingService.handleErrors));
	}

	postStatusEmailRequest(emailStatusRequest: EmailStatusRequest): Observable<EmailStatusResponse> {
		const url = `${environment.serviceApiUrl}/myestes/ShipmentTracking/v1.0/email`;
		return this.httpClient
			.post<EmailStatusResponse>(url, JSON.stringify(emailStatusRequest))
			.pipe(catchError(ShipmentTrackingService.handleErrors));
	}

  postShipmentImagesRequest(shipmentImageRequest: ShipmentImageRequest, type: String): Observable<ShipmentImageResponse[]> {
		const url = `${environment.serviceApiUrl}/myestes/ShipmentTracking/v1.0/images/${type}`;
		return this.httpClient
			.post<ShipmentImageResponse[]>(url, JSON.stringify(shipmentImageRequest))
			.pipe(catchError(ShipmentTrackingService.handleErrors));
  }

  downloadDocumentWeightAndResearch(fileName: string) {
		let headers = new HttpHeaders();
		headers = headers.set('Accept', 'application/pdf');
		return this.httpClient.get(
			`${environment.serviceApiUrl}/myestes/ImageViewing/v1.0/wr/download?fileName=${fileName}`,
			{ responseType: 'blob', headers: headers }
		);
	}

	getDocumentDetailsWeightAndResearch(proNumbers: Array<string>): Observable<Array<WRCertificate>> {
    return this.httpClient.post<Array<WRCertificate>>(
      `${environment.serviceApiUrl}/myestes/ImageViewing/v1.0/wr/documentDetails`,
      proNumbers
    );
  }
}
