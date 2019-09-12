import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { InvoiceInquiryService } from '../invoice-inquiry.service';
import { AgingDetail } from '../models/aging-detail.model';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { Subscription } from 'rxjs';
import { Image } from '../models/image.model';
import { PaymentReason } from '../models/payment-reason.model';
import { PaymentLimits } from '../models/payment-limits.model';
import { Payment } from '../models/payment.model';
import {CurrencyPipe} from '@angular/common';
import {
  SnackbarService,
  FormService,
  FeedbackTypes
} from 'common';

import {
  FormBuilder,
  FormControl,

  Validators
} from '@angular/forms';
import { animate, state, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-pay-invoices',
  templateUrl: './pay-invoices.component.html',
  styleUrls: ['./pay-invoices.component.scss'],
  animations: [
    trigger('detailExpand', [
      state(
        'collapsed',
        style({
          height: '0px',
          minHeight: '0px',
          display: 'none',
          border: 'none'
        })
      ),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)'))
    ])
  ]
})
export class PayInvoicesComponent implements OnInit, OnDestroy {
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<Payment>;
  selections: AgingDetail[];
  displayedColumns: string[];
  statusSubs: Subscription[];
  images: Image[];
  paymentReasons: PaymentReason[];
  paymentLimits: PaymentLimits;
  loading: boolean;
  totalPaymentAmount: number;
  errorMessages: [FeedbackTypes, string];
  reasonSub: Subscription;
  limitSub: Subscription;
  paymentSub: Subscription;
  maxLimitString: string;
  minLimitString: string;

  constructor(
    private invoiceInquiryService: InvoiceInquiryService,
    private fb: FormBuilder,
    public formService: FormService,
    private snackbarService: SnackbarService,
    private currencyPipe: CurrencyPipe
  ) {}

  ngOnInit() {
    window.scrollTo({ top: 0, behavior: 'smooth' });

    this.displayedColumns = [
      'pro',
      'invoiceDate',
      'statementNum',
      'openAmount',
      'pendingPayAmount',
      'payment',
      'action'
    ];

    this.selections = this.invoiceInquiryService.getSelectedAgingDetails() || [];
    this.dataSource = new MatTableDataSource([]);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.totalPaymentAmount = 0;


    this.reasonSub = this.invoiceInquiryService.getPaymentReasons().subscribe(data => {
      this.paymentReasons = data;
    });
    this.loading = true;
    this.limitSub = this.invoiceInquiryService.getPaymentLimits().subscribe(data => {
      this.paymentLimits = data;
      this.minLimitString = this.currencyPipe.transform(this.paymentLimits.minimum, 'USD');
      this.maxLimitString = this.currencyPipe.transform(this.paymentLimits.maximum, 'USD');
      this.loading = false;
      this.initializeTableForm();
    });
  }

  ngOnDestroy() {
    if (this.limitSub) {
      this.limitSub.unsubscribe();
    }
    if (this.reasonSub) {
      this.reasonSub.unsubscribe();
    }
    if (this.paymentSub) {
      this.paymentSub.unsubscribe();
    }
  }

  initializeTableForm() {
    let data = [];
    this.selections.forEach(selection => {
      let payment = new Payment();
      payment.explain = '';
      payment.invoiceDate = selection.invoiceDate;
      payment.openAmount = selection.openAmount;
      payment.pendingPayAmount = selection.pendingPayAmount;
      payment.payment = Number(selection.openAmount) - Number(selection.pendingPayAmount);
      payment.pro = selection.pro;
      payment.reasonCode = '';
      payment.statementNum = selection.statementNum;
      (payment as any).adjustAmount = new FormControl(false);

      const paymentAmount = Number(selection.openAmount) - Number(selection.pendingPayAmount);
      let maximum = Math.min(paymentAmount, this.paymentLimits.maximum);
      let minimum = Math.min(maximum, this.paymentLimits.minimum);

      (payment as any).fg = this.fb.group({
        payment: [
          paymentAmount,
          [Validators.max(maximum), Validators.min(minimum)]
        ],
        reasonCode: [''],
        explain: ['']
      });
      data.push(payment);

      this.totalPaymentAmount += payment.payment;
    });

    this.dataSource = new MatTableDataSource(data);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  toggleAdjustAmount(element: any) {
    if (!element.adjustAmount.value) {
      element.fg.get('payment').setValue(element.openAmount - element.pendingPayAmount);
      this.recalculateTotalAmount();
    }
  }

  recalculateTotalAmount() {
    this.totalPaymentAmount = 0;
    this.dataSource.data.forEach(payment => {
      this.totalPaymentAmount += payment.fg.get('payment').value;
    });
  }

  verifyPayments() {
    let invalid = false;
    this.errorMessages = null;
    let payments: Payment[] = [];
    this.dataSource.data.forEach(payment => {
      if ((payment as any).fg.invalid) {
        invalid = true;
        Object.keys(payment.fg.controls).forEach(key => {
          payment.fg.get(key).markAsTouched();
        });
      } else {
        const newPayment = new Payment();
        newPayment.explain = payment.fg.controls['explain'].value;
        newPayment.reasonCode = payment.fg.controls['reasonCode'].value;
        newPayment.payment = payment.fg.controls['payment'].value;
        newPayment.pendingPayAmount = payment.pendingPayAmount;
        newPayment.pro = payment.pro;
        newPayment.openAmount = payment.openAmount;
        payments.push(newPayment);
      }
    });

    if (this.totalPaymentAmount > this.paymentLimits.maximum || this.totalPaymentAmount < this.paymentLimits.minimum) {
      this.errorMessages = ['error', `Total pay amount must be between ${this.minLimitString} and ${this.maxLimitString}. Please modify payment selection.`];
    }
    else if (!invalid) {
      this.loading = true;
      this.paymentSub = this.invoiceInquiryService.verifyPayment(payments).subscribe(
        data => {
          if (data.redirectUrl) {
            window.location.href = data.redirectUrl;
          }
        },
        err => {
          this.snackbarService.error('Failure');
        }
      );
    }
  }
}
