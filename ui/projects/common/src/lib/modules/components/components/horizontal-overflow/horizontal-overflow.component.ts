import { Component, OnInit, ElementRef, HostListener, AfterViewInit } from '@angular/core';

@Component({
  selector: 'lib-horizontal-overflow',
  templateUrl: './horizontal-overflow.component.html',
  styleUrls: ['./horizontal-overflow.component.scss']
})
export class HorizontalOverflowComponent implements OnInit, AfterViewInit {
  elWidth: string;

  // @HostListener('window:resize', ['$event.target'])
  // onResize() {
  //   this.elWidth = this.el.nativeElement.width;
  // }

  // @HostListener('window:resize', ['$event'])
  // onResize(event) {
  //   this.elWidth = this.el.nativeElement.offsetWidth;
  //   // console.log(event.target.offsetWidth);

  //   if (this.el.nativeElement.scrollWidth > this.el.nativeElement.offsetWidth) {
  //     console.log(`show`);
  //   }
  // }

  constructor(private el: ElementRef) {}

  ngOnInit() {}

  ngAfterViewInit() {
    // console.log(this.el);
    // this.elWidth = this.el.nativeElement.offsetWidth;
  }
}
