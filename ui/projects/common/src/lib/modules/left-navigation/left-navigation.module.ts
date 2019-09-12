import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LeftNavigationComponent } from './components/left-navigation.component';
import { LeftNavigationService } from './services/left-navigation.service';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [CommonModule, HttpClientModule],
  declarations: [LeftNavigationComponent],
  exports: [LeftNavigationComponent],
  providers: [LeftNavigationService]
})
export class LeftNavigationModule {}
