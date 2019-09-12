// import {
//   Component,
//   OnInit,
//   Input,
//   ContentChildren,
//   TemplateRef,
//   QueryList,
//   AfterViewInit
// } from '@angular/core';

// class TableRowConfig {
//   column: string;
//   header: string;
// }

// class TableData {
//   data: any[];
//   config: any[];
// }

// // ['pro','date']
// /**
//  * [
//  *    {
//  *        pro: 234234,
//  *        date: 12/12/2018
//  *    }
//  *
//  * ]
//  */

// @Component({
//   selector: 'lib-table',
//   templateUrl: './table.component.html',
//   styleUrls: ['./table.component.scss']
// })
// export class TableComponent implements OnInit, AfterViewInit {
//   @Input() displayedColumns: Array<string> = ['pro', 'date'];
//   @Input() data: Array<any> = [
//     { pro: 132131, date: '12/12/2018' },
//     { pro: 132131, date: '12/12/2018' },
//     { pro: 132131, date: '12/12/2018' }
//   ];
//   @ContentChildren(TemplateRef) templates: QueryList<TemplateRef<any>>;

//   constructor() {}

//   ngOnInit() {}

//   ngAfterViewInit() {
//     console.log(this.templates);
//     console.log(
//       this.templates.find((template: any) => {
//         console.log(template.name);
//         return template.name === 'test1';
//       })
//     );
//   }
// }
