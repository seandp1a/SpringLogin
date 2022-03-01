import { BackendService } from './../service/backend.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent implements OnInit {

  constructor(
    private backendSvc :BackendService
  ) { }
  public data:Data[] = [];
  public sortType: keyof Data = 'id';
  public sortReverse: boolean = false;

  public sortBy(type: keyof Data, reverse: boolean) {
    return this.data.sort((a: Data, b: Data) => {
      if (reverse) {
        return a[type] > b[type] ? -1 : a[type] === b[type] ? 0 : 1
      } else {
        return a[type] > b[type] ? 1 : a[type] === b[type] ? 0 : -1
      }
    });
  }

  public changeSortType(type: keyof Data) {
    if (this.sortType === type) {
      this.sortReverse = !this.sortReverse;
      return;
    }
    this.sortType = type;
    this.sortReverse = false;
  }
  ngOnInit(): void {

  }
}
export interface Data {
  id: number,
  name: string,
  age:string,
  create_date: string
}
