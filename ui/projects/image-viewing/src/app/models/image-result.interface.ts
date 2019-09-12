import { ImageStatusEnum } from './image-status.enum';
import { Image } from './image.interface';

export interface ImageResult {
  docType: string;
  key1: string;
  key2: string;
  key3: string;
  key4: string;
  key5: string;
  requestNumber: string;
  searchData: string;
  status: ImageStatusEnum;
  imageDetails: Image[];
  errorMessage: string | null;
}
