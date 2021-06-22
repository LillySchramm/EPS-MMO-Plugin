import { Component, OnInit } from '@angular/core';
import { APIService } from 'src/app/services/api.service';
import { NPC } from 'src/common/npcType';

@Component({
  selector: 'app-npc-overview',
  templateUrl: './npc-overview.component.html',
  styleUrls: ['./npc-overview.component.css']
})
export class NpcOverviewComponent implements OnInit {

  npcs : NPC[] = [];

  constructor(private api:APIService) {
  }

  ngOnInit(): void {
    this.api.getAllNPC().subscribe((res) => {
      this.npcs = res.npc
    })
  }

}
 