import { Component, OnInit, Inject, OnDestroy } from '@angular/core';
import { QuickLink } from '../../models';
import { Observable, Subscription, Subject } from 'rxjs';
import { QuickLinksService } from '../../services/quick-links.service';
import { AuthActionEnum } from '../../../auth/public_api';
import { links, SimpleQuickLink } from './links.model';
import { APP_CONFIG } from '../../../../app.config';
import { AppConfig } from '../../../../models/app-config.interface';
import { map, switchMap, takeUntil, take, distinctUntilChanged } from 'rxjs/operators';
import { AuthService } from '../../../auth/public_api';

@Component({
  selector: 'lib-quick-links',
  templateUrl: './quick-links.component.html',
  styleUrls: ['./quick-links.component.scss']
})
export class QuickLinksComponent implements OnInit, OnDestroy {
  private stop$ = new Subject<boolean>();
  quickLinks$: Observable<Array<SimpleQuickLink>>;

  constructor(
    @Inject(APP_CONFIG) private appConfig: AppConfig,
    public linkService: QuickLinksService,
    public auth: AuthService
  ) {}

  ngOnInit() {

    this.quickLinks$ = this.linkService.addedLinks$.pipe(
      map(v => {
        const simpleList: Array<SimpleQuickLink> = v.reduce((prev, curr) => {
          const link = links[curr.appName];

          if (!link) {
            throw new Error(
              `No configured application for user's configured preferred link: ${curr.appName}`
            );
          }

          const simpleLink: SimpleQuickLink = {
            isApplication: link.isApplication,
            icon: link.icon,
            description: curr.description,
            path: link.path,
            appName: curr.appName
          };

          prev.push(simpleLink);
          return prev;
        }, []);

        return simpleList;
      }),
      takeUntil(this.stop$)
    );

    this.auth.authAction$
      .pipe(
        distinctUntilChanged(),
        switchMap(() => this.linkService.getQuickLinks()),
        takeUntil(this.stop$)
      )
      .subscribe();
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  getClasses(appName: string) {
    const icon = this.getLinkIcon(appName);
    return {
      'link-icon': true,
      [icon]: icon ? true : false
    };
  }

  getLinkIcon(appName: string) {
    const info = this.getLinkInfo(appName);
    if (info) {
      return info.icon;
    } else {
      return `fas fa-file-alt`;
    }
  }

  private getLinkInfo(appName: string) {
    return links[appName];
  }

  getAppRoute(appName: string) {
    const link = links[appName];
    let route = ``;

    if (link) {
      if (link.isApplication === true) {
        route = link.uri
          ? `${this.appConfig.cmsUrl}${link.uri}`
          : `${this.appConfig.appBaseUrl}${link.path}`;
      } else {
        route = `${link.path}`;
      }
    }

    return route;
  }
}
