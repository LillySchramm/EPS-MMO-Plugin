import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-pos-helper',
  templateUrl: './pos-helper.component.html',
  styleUrls: ['./pos-helper.component.css']
})
export class PosHelperComponent implements OnInit {

  @Input() pos : string = "0>>0>>0"; 

  x : number = 0
  y : number = 0
  z : number = 0

  constructor() { }

  ngOnInit(): void {
    let tmp = this.pos.split(">>")

    this.x = parseInt(tmp[0])
    this.y = parseInt(tmp[1])
    this.z = parseInt(tmp[2])
  }

}
