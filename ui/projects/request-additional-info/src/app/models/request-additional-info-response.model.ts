export class RequestAdditionalInfoResponse {
  message: string;
  errorCode: string;

  constructor(message, errorCode) {
    this.message = message;
    this.errorCode = errorCode;
  }
}