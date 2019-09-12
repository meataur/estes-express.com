import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'lib-form-header',
  templateUrl: './form-header.component.html',
  styleUrls: ['./form-header.component.scss']
})
export class FormHeaderComponent implements OnInit {
  @Input() iconSrc: string;
  @Input() faClass: string;
  @Input() color: 'red' | 'black' = 'black';

  constructor() {}

  ngOnInit() {}

  getClasses() {
    return {
      'form-header': true,
      'form-header--black': this.color === 'black',
      'form-header--red': this.color === 'red'
    };
  }
}
