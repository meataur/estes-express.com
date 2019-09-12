import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QuickLinksComponent } from './components/quick-links/quick-links.component';
import { ManageQuickLinksComponent } from './components/manage-quick-links/manage-quick-links.component';
import { QuickLinksService } from './services/quick-links.service';
import { QuickLinksRoutingModule } from './quick-links-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { ComponentsModule } from '../components/components.module';
import { UtilModule } from '../../util/util.module';

@NgModule({
  imports: [CommonModule, ComponentsModule, HttpClientModule, QuickLinksRoutingModule, UtilModule],
  declarations: [QuickLinksComponent, ManageQuickLinksComponent],
  exports: [QuickLinksComponent]
})
export class QuickLinksModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: QuickLinksModule,
      providers: [
        QuickLinksService
      ]
    };
  }
}

