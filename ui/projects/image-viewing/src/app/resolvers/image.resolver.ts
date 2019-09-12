import {
  Resolve,
  ActivatedRouteSnapshot,
  RouterStateSnapshot
} from '@angular/router';
import { Image, ImageDetailsRequest } from '../models';
import { ImageViewingService } from '../services/image-viewing.service';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable()
export class ImageResolver implements Resolve<Array<Image>> {
  constructor(private imageViewingService: ImageViewingService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> | Promise<any> | any {
    const payload = new ImageDetailsRequest();
    payload.docType = route.paramMap.get('docType');
    payload.key1 = route.paramMap.get('key1');
    payload.key2 = route.paramMap.get('key2');
    payload.key3 = route.paramMap.get('key3');
    payload.key4 = route.paramMap.get('key4');
    payload.key5 = route.paramMap.get('key5');

    return this.imageViewingService.getImageDetails(payload);
  }
}
