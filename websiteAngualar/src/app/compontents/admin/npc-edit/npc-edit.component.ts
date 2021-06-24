import { Component, OnInit, Input } from '@angular/core';
import { APIService } from 'src/app/services/api.service';
import { NPC } from 'src/common/npcType';

@Component({
  selector: 'app-npc-edit',
  templateUrl: './npc-edit.component.html',
  styleUrls: ['./npc-edit.component.css']
})
export class NpcEditComponent implements OnInit {

  @Input() npc : NPC;
  _npc : NPC;
  texture_data : string = ""
  texture_signature : string = ""

  constructor(private api:APIService) {
    this.npc = {
      ID:1,
      NAME:"",
      POS:"",
      ROTATION:"",
      SCRIPT:"",
      SKIN:""
    }

    this._npc = {...this.npc};
  }

  ngOnInit(): void {
    if(this.npc){
      this.texture_data = this.npc.SKIN.split("<!>")[0]
      this.texture_signature = this.npc.SKIN.split("<!>")[1]
    }

    setTimeout(() => {this.updateSkin()}, 100);
  }

  updateSkin() : void {   
    this.npc.SKIN = this.texture_data + "<!>" + this.texture_signature;    
    setTimeout(() => {this.updateSkin()}, 100);
  }

  onSubmit(): void {   
    if(this.npc.NAME != this._npc.NAME && this.npc.NAME.length >= 1){
      this.api.saveNPC(this.npc.ID, "SKIN", this.npc.SKIN);      
    }
    if(this.npc.NAME != this._npc.SKIN && this.npc.SKIN.length >= 1){
      this.api.saveNPC(this.npc.ID, "SKIN", this.npc.SKIN);      
    }
  }
}
