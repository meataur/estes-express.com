import { HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs/internal/observable/throwError';
import { Observable } from 'rxjs';

export class MyEstesGlobalHandlers {
  /**
   * handles errors returned by the Estes services.
   * @param error is the error returned by the service
   */
	static handleErrors(error: HttpErrorResponse): Observable<any> {
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
}