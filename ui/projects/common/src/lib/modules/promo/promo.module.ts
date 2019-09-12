import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PromoService } from './services/promo.service';
import { LeftPromoComponent } from './components/leftpromo.component';
import { PromoComponent } from './components/promo.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [CommonModule, HttpClientModule],
  declarations: [PromoComponent, LeftPromoComponent],
  exports: [PromoComponent, LeftPromoComponent],
  providers: [PromoService]
})
export class PromoModule {}
