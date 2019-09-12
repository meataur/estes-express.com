import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CmsAlertService } from './services/cms-alert.service';
import { CmsAlertComponent } from './components/cms-alert.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [CommonModule, HttpClientModule],
  declarations: [CmsAlertComponent],
  exports: [CmsAlertComponent],
  providers: [CmsAlertService]
})
export class CmsAlertModule {}
