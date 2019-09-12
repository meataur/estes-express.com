export class ImageRetrievalRequest {
  deliveryMethod: 'email' | 'fax';
  destZip: string;
  documentTypes: string;
  email: string;
  fax: string;
  faxAttention: string;
  trackingNum: string;
}
