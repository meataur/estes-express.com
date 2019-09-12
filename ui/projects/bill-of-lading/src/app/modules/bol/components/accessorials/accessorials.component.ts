import { Component, OnInit, OnDestroy, OnChanges, SimpleChanges } from '@angular/core';
import { DialogService } from 'common';
import { AccessorialsModalComponent } from '../accessorials-modal/accessorials-modal.component';
import { SelectionModel } from '@angular/cdk/collections';
import { Accessorial, BolSection, BillOfLading } from '../../models';
import { PickupRequestFormService } from '../../services/pickup-request-form.service';
import { Subscription, Subject } from 'rxjs';
import { AccessorialsFormService } from '../../services/accessorials-form.service';
import { map, takeUntil } from 'rxjs/operators';
import { QuoteDetailsFormService } from '../../services/quote-details.service';

@Component({
  selector: 'app-accessorials',
  templateUrl: './accessorials.component.html',
  styleUrls: ['./accessorials.component.scss']
})
export class AccessorialsComponent extends BolSection implements OnInit, OnDestroy, OnChanges {
  private stop$ = new Subject<boolean>();
  accessorials: Array<Accessorial> = [];
  commonAccessorials: Array<Accessorial>;
  selection = new SelectionModel<Accessorial>(true, []);
  showAllAccessorials = false;
  hasNonPopularAccessorialsSelected = false;
  pickupAccessorialSub: Subscription;
  formSub: Subscription;

  constructor(
    private dialogService: DialogService,
    private pickupRequestFormService: PickupRequestFormService,
    private accessorialsFormService: AccessorialsFormService,
    private quoteFormService: QuoteDetailsFormService
  ) {
    super();
  }

  ngOnInit() {
    this.accessorialsFormService
      .getAccessorials()
      .pipe(
        map(res => {
          res.sort((a, b) => {
            if (a.description < b.description) {
              return -1;
            } else if (a.description > b.description) {
              return 1;
            } else {
              return 0;
            }
          });

          return res;
        })
      )
      .subscribe(next => {
        this.commonAccessorials = next.filter(a => a.display === true);
        this.accessorials = [...next];
        this.initFormSub();
      });

    this.pickupAccessorialSub = this.pickupRequestFormService.pickupAccessorialSelected$.subscribe(
      isSelected => {
        if (isSelected) {
          this.showAllAccessorials = true;
        } else {
          if (!this.isNonCommonAccessorialSelected()) {
            this.showAllAccessorials = false;
          }
        }
      }
    );
  }

  initFormSub() {
    this.formSub = this.accessorialsFormService.accessorialsForm$.subscribe(next => {
      this.selection.clear();
      // For each item in selection, find the corresponding instance in the accessorials list, remove the original
      // item, and toggle (add) the item match found in the accessorials list.
      for (const s of next.selected) {
        const match = this.accessorials.find(a => a.code.toLowerCase() === s.code.toLowerCase());
        next.deselect(s);
        if (match) {
          next.select(match);
        }
      }

      this.selection = next;
      this.accessorialsFormService.initModifiedWatch();
    });

    this.quoteFormService.rateQuote$.pipe(takeUntil(this.stop$)).subscribe(next => {
      if (next) {
        let incAccessorials = [];
        if (next.accessorials && next.accessorials.length) {
          incAccessorials = next.accessorials.reduce((accum, curr) => {
            accum.push(curr.accessorial);
            return accum;
          }, []);
        }
        this.accessorialsFormService.resetForm(incAccessorials);
      }
    });
  }

  // private reconcile

  private isNonCommonAccessorialSelected(): boolean {
    for (const s of this.selection.selected) {
      if (
        typeof this.commonAccessorials.find(
          ca => ca.code.toLowerCase() === s.code.toLowerCase()
        ) === 'undefined'
      ) {
        return true;
      }
    }
    return false;
  }

  ngOnDestroy() {
    if (this.pickupAccessorialSub) {
      this.pickupAccessorialSub.unsubscribe();
    }
    if (this.formSub) {
      this.formSub.unsubscribe();
    }
    this.stop$.next(true);
    this.stop$.unsubscribe();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.draft && changes.draft.currentValue) {
      this.accessorialsFormService.resetForm(
        (changes.draft.currentValue as BillOfLading).accessorials
      );
    }
  }

  isChecked(a: Accessorial) {
    return this.selection.selected.find(ac => ac.code.toLowerCase() === a.code.toLowerCase());
  }

  onChecked(a: Accessorial) {
    this.selection.toggle(
      this.accessorials.find(ac => ac.code.toLowerCase() === a.code.toLowerCase())
    );

    if (this.selection.selected.length > 0) {
      try {
        this.hasNonPopularAccessorialsSelected =
          this.selection.selected.filter(
            acc =>
              typeof this.commonAccessorials.find(
                ca => ca.code.toLowerCase() === acc.code.toLowerCase()
              ) === 'undefined'
          ).length > 0;
      } catch (e) {
        console.log(e);
      }
    } else {
      this.hasNonPopularAccessorialsSelected = false;
    }
  }

  openAccessorialsDialog() {
    this.dialogService
      .prompt(AccessorialsModalComponent, this.selection, 'accessorial-dialog')
      .subscribe(next => {
        if (next) {
          this.selection = next;
        }
      });
  }

  toggleAllAccessorials() {
    this.showAllAccessorials = !this.showAllAccessorials;
  }
}
