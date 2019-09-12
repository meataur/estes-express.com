import { ImageStatusEnum } from './image-status.enum';

export interface ImageStatusResponse {
  docType: string;
  requestNumber: string;
  searchData: string;
  status: ImageStatusEnum;
}
