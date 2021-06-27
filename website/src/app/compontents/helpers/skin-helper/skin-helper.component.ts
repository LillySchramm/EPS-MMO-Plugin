import { Component, OnInit, Input, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-skin-helper',
  templateUrl: './skin-helper.component.html',
  styleUrls: ['./skin-helper.component.css']
})
export class SkinHelperComponent implements OnInit {

  @Input() skin : string = ""
  @Input() type : string = ""

  id: string = ""

  constructor() { }

  ngOnInit(): void {
    this.updateID();
  }

  updateID(){
    let json : JSON = JSON.parse(atob(this.skin.split("<!>")[0]));
    let url : string = (<any>json)["textures"]["SKIN"]["url"]

    this.id = url.split("/")[4]
  }

  ngOnChanges(changes: SimpleChanges) {
    this.updateID()
  }
}
