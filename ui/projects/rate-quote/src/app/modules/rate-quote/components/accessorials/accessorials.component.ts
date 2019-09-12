import { Component, OnInit, OnDestroy, OnChanges, SimpleChanges } from '@angular/core';
import { DialogService, AuthService } from 'common';
import { SelectionModel } from '@angular/cdk/collections';
import { Subscription } from 'rxjs';
import { map, takeUntil } from 'rxjs/operators';
import { RateQuoteFormSection, Accessorial } from '../../models';
import { AccessorialsService } from '../../services/accessorials.service';

@Component({
  selector: 'app-accessorials',
  templateUrl: './accessorials.component.html',
  styleUrls: ['./accessorials.component.scss']
})
export class AccessorialsComponent extends RateQuoteFormSection
  implements OnInit, OnDestroy, OnChanges {
  accessorials: Array<Accessorial> = [];
  commonAccessorials: Array<Accessorial>;
  selection = new SelectionModel<Accessorial>(true, []);
  showAllAccessorials = false;
  hasNonPopularAccessorialsSelected = false;
  pickupAccessorialSub: Subscription;
  formSub: Subscription;

  constructor(private accessorialsService: AccessorialsService, protected auth: AuthService) {
    super(auth);
  }

  ngOnInit() {
    super.ngOnInit();
  }

  // Invoked by parent class in ngOnInit()
  initForm() {
    this.accessorialsService
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
        }),
        // Remove duplicates witht the same code.
        map(res => {
          return res.reduce((accum: Accessorial[], curr) => {
            if (typeof accum.find(e => e.code === curr.code) === 'undefined') {
              accum.push(curr);
            }
            return accum;
          }, []);
        }),
        takeUntil(this.stop$)
      )
      .subscribe(next => {
        this.commonAccessorials = next.filter(a => a.display === true);
        this.accessorials = [...next];
        this.initFormSub();
      });
  }

  initFormSub() {
    this.formSub = this.accessorialsService.accessorialsForm$
      .pipe(takeUntil(this.stop$))
      .subscribe(next => {
        this.selection.clear();
        // For each item in selection, find the corresponding instance in the accessorials list, remove the original
        // item, and toggle (add) the item match found in the accessorials list.
        for (const s of next.selected) {
          const match = this.accessorials.find(a => a.code.toLowerCase() === s.code.toLowerCase());
          // Deselect the incoming selected element, regardless if it is found in the accessorials
          // list or not.  This will prevent an error being thrown if a user tries to deselect
          // an item that does not exist in the current list.
          next.deselect(s);
          if (match) {
            next.select(match);
          }
        }

        this.selection = next;
      });
  }

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
    super.ngOnDestroy();
    if (this.pickupAccessorialSub) {
      this.pickupAccessorialSub.unsubscribe();
    }
    if (this.formSub) {
      this.formSub.unsubscribe();
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.quote && changes.quote.currentValue) {
      this.accessorialsService.resetForm(changes.quote.currentValue);
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

  toggleAllAccessorials() {
    this.showAllAccessorials = !this.showAllAccessorials;
  }
}
