import { Injectable, ElementRef } from '@angular/core';
import { Overlay, OverlayRef } from '@angular/cdk/overlay';
import { OverlayComponent } from '../components/overlay/overlay.component';
import { ComponentPortal } from '@angular/cdk/portal';
import { OverlayContainer } from '@angular/cdk/overlay';
import {
  OverlayKeyboardDispatcher,
  OverlayPositionBuilder,
  ScrollStrategyOptions
} from '@angular/cdk/overlay';
import {
  ComponentFactoryResolver,
  Inject,
  Injector,
  NgZone,
  Renderer2,
  RendererFactory2
} from '@angular/core';
import { Directionality } from '@angular/cdk/bidi';
import { DOCUMENT } from '@angular/common';
import { Subscription, timer } from 'rxjs';

// See https://medium.com/@manojvignesh/content-specific-progress-loading-indicator-using-cdk-overlay-207d14b603b5
export class DynamicOverlayContainer extends OverlayContainer {
  public setContainerElement(containerElement: HTMLElement): void {
    this._containerElement = containerElement;
  }
}

// See https://medium.com/@manojvignesh/content-specific-progress-loading-indicator-using-cdk-overlay-207d14b603b5
export class DynamicOverlay extends Overlay {
  private readonly _dynamicOverlayContainer: DynamicOverlayContainer;
  private renderer: Renderer2;

  constructor(
    scrollStrategies: ScrollStrategyOptions,
    _overlayContainer: DynamicOverlayContainer,
    _componentFactoryResolver: ComponentFactoryResolver,
    _positionBuilder: OverlayPositionBuilder,
    _keyboardDispatcher: OverlayKeyboardDispatcher,
    _injector: Injector,
    _ngZone: NgZone,
    @Inject(DOCUMENT) _document: any,
    _directionality: Directionality,
    rendererFactory: RendererFactory2
  ) {
    super(
      scrollStrategies,
      _overlayContainer,
      _componentFactoryResolver,
      _positionBuilder,
      _keyboardDispatcher,
      _injector,
      _ngZone,
      _document,
      _directionality
    );
    this.renderer = rendererFactory.createRenderer(null, null);

    this._dynamicOverlayContainer = _overlayContainer;
  }

  public setContainerElement(containerElement: HTMLElement): void {
    this.renderer.setStyle(containerElement, 'transform', 'translateZ(0)');
    this._dynamicOverlayContainer.setContainerElement(containerElement);
  }
}

@Injectable()
export class OverlayService {
  overlayTarget: ElementRef;

  private overlayRef: OverlayRef;

  constructor(private dynamicOverlay: DynamicOverlay) {}

  public showProgress(elRef: ElementRef) {
    if (elRef) {
      const result: ProgressRef = { subscription: null, overlayRef: null };
      result.subscription = timer(500).subscribe(() => {
        this.dynamicOverlay.setContainerElement(elRef.nativeElement);
        const positionStrategy = this.dynamicOverlay
          .position()
          .global()
          .centerHorizontally()
          .centerVertically();
        result.overlayRef = this.dynamicOverlay.create({
          positionStrategy: positionStrategy,
          panelClass: 'estes-overlay',
          hasBackdrop: true,
          backdropClass: 'estes-overlay-container'
        });
        result.overlayRef.attach(new ComponentPortal(OverlayComponent));
      });
      return result;
    } else {
      return null;
    }
  }

  detach(result: ProgressRef) {
    if (result) {
      result.subscription.unsubscribe();
      if (result.overlayRef) {
        result.overlayRef.detach();
      }
    }
  }
}

// tslint:disable-next-line:interface-over-type-literal
export declare type ProgressRef = {
  subscription: Subscription;
  overlayRef: OverlayRef;
};
