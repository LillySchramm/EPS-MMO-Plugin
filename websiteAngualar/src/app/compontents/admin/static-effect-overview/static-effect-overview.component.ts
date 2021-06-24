import { Component, OnInit } from '@angular/core';
import { APIService } from 'src/app/services/api.service';
import { StaticEffect } from 'src/common/staticEffectType';

@Component({
  selector: 'app-static-effect-overview',
  templateUrl: './static-effect-overview.component.html',
  styleUrls: ['./static-effect-overview.component.css']
})
export class StaticEffectOverviewComponent implements OnInit {

  cur_index : number = 0;
  cur_edit : number = -1; 
  
  sites : Array<StaticEffect[]> = [];
  effects : StaticEffect[] = [];

  constructor(private api:APIService) { }

  ngOnInit(): void {
    this.api.getAllStaticEffects().subscribe((e) => {
      this.effects = e.npc;

      this.gererateSites()
    })
  }

  gererateSites() : void{
    let cur : StaticEffect[] = [] 
    this.effects.forEach(e => {
      cur.push(e)
      if(cur.length == 10){
        this.sites.push(cur);
        cur = []
      }
    })

    if(cur != []){
      this.sites.push(cur)
    }
  }

  getTypeName(e : StaticEffect) : string{
    switch(e.DATA.split(">>")[0]){
      case "single":
        return "Single Point"
      case "circle":
        return "Circle"
      case "pillar":
        return "Pillar"
    }

    return "UNKNOWN TYPE"; 
  }

  changeSite(offset : number) : void{
    this.cur_index += offset;
  }

  changeEditEffect(n : number) : void{
    this.cur_edit = n
  }

}
