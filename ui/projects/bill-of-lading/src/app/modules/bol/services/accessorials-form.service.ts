import { Injectable, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { BehaviorSubject, Observable, Subject, Subscription, merge } from 'rxjs';
import { BolSectionService, Accessorial, BillOfLading } from '../models';
import { tap, takeUntil, filter, take } from 'rxjs/operators';
import { BolService } from './bol.service';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { SelectionModel } from '@angular/cdk/collections';

@Injectable({
  providedIn: 'root'
})
export class AccessorialsFormService extends BolSectionService implements OnDestroy {
  private stop$ = new Subject<boolean>();
  private accessorialsForm: BehaviorSubject<
    SelectionModel<Accessorial> | undefined
  > = new BehaviorSubject(new SelectionModel<Accessorial>(true, []));
  accessorialsForm$: Observable<
    SelectionModel<Accessorial>
  > = this.accessorialsForm.asObservable().pipe(
    tap(selection => {
      this.stop$.next(true);

      // if (this.bolService.modified !== true) {
      //   selection.onChange
      //     .pipe(
      //       take(1),
      //       takeUntil(merge(this.stop$, this.bolService.stopModified$))
      //     )
      //     .subscribe(() => {
      //       console.log(`Form modified: Accessorials`);
      //       this.bolService.setModified();
      //     });
      // }
    })
  );

  constructor(private fb: FormBuilder, private bolService: BolService, private http: HttpClient) {
    super();
  }

  getAccessorials(): Observable<Array<Accessorial>> {
    return this.http.get<Array<Accessorial>>(
      `${environment.serviceApiUrl}/myestes/BillOfLading/v1.0/accessorials`
    );
  }

  initModifiedWatch() {
    const selection = this.accessorialsForm.getValue();

    if (this.bolService.modified !== true) {
      selection.changed
        .pipe(
          take(1),
          takeUntil(merge(this.stop$, this.bolService.stopModified$))
        )
        .subscribe(() => {
          this.bolService.setModified();
        });
    }
  }

  ngOnDestroy() {
    this.stop$.next(true);
    this.stop$.unsubscribe();
    this.accessorialsForm.unsubscribe();
  }

  valid(): boolean {
    return true;
  }

  populateModel(bol: BillOfLading) {
    const accessorialSelection = this.accessorialsForm.getValue();

    bol.accessorials = [];

    if (accessorialSelection.selected.length) {
      bol.accessorials = accessorialSelection.selected;
    }
  }

  resetForm(a: Array<Accessorial> = []) {
    // For any accessorials that don't have a code set from services, remove that from the array.
    a = a.reduce((accum, curr) => {
      if (!!curr.code) {
        accum.push(curr);
      }
      return accum;
    }, []);
    const selection = new SelectionModel<Accessorial>(true, a);

    this.accessorialsForm.next(selection);
  }
}
