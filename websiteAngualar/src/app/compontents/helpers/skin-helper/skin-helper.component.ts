import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-skin-helper',
  templateUrl: './skin-helper.component.html',
  styleUrls: ['./skin-helper.component.css']
})
export class SkinHelperComponent implements OnInit {

  @Input() skin : string = ""
  @Input() type : string = ""

  id: string = "1e82fdc019c773958deba9b72bf90bc89141dd7723672fb6b59dcc88a178dc9d"

  constructor() { }

  ngOnInit(): void {    
    let json : JSON = JSON.parse(atob(this.skin.split("<!>")[0]));    
    let url : string = (<any>json)["textures"]["SKIN"]["url"]

    this.id = url.split("/")[4]
    
  }

}
