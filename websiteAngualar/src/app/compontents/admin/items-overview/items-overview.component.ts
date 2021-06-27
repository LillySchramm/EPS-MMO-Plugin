import { Component, OnInit } from '@angular/core';
import { APIService } from 'src/app/services/api.service';
import { Item } from 'src/common/itemType';

@Component({
  selector: 'app-items-overview',
  templateUrl: './items-overview.component.html',
  styleUrls: ['./items-overview.component.css']
})
export class ItemsOverviewComponent implements OnInit {

  private ITEMS_PER_SITE = 10

  ItemsOverviewComponent = ItemsOverviewComponent

  cur_edit : number = -1;
  cur_index : number = 0;
  cur_category : string = "UI"

  _temp_name : string = ""

  resourcepack_version : number = 0
  resourcepack_last_regen : string = "nice string" 

  private items : Item[] = []
  sites : Map<string, Array<Item[]>> = new Map()
  public static types : Map<string, string> = new Map([
    ["general", "General"],
    ["UI","UI"]
  ])

  constructor(private api:APIService) { }
  
  ngOnInit(): void {
    this.loadRPVersion()
    this.loadElements()
  }

  loadRPVersion() : void {
    this.api.getResourcepackVersion().subscribe((response) => {
      this.resourcepack_version = response.ver;
      this.resourcepack_last_regen = response.last_changed;
    })
  }

  loadElements(jumpToLast? : boolean) : void {
    ItemsOverviewComponent.types.forEach((v ,k) => {
      this.api.getAllItems(k).subscribe((resp) => {

        let temp_site : Item[] = []
        let temp_sites : Array<Item[]> = []        

        resp.items.forEach((item) => {
          temp_site.push(item)
          this.items.push(item)

          if(temp_site.length == this.ITEMS_PER_SITE){
            temp_sites.push(temp_site)
            temp_site = []
          }
        })

        if(temp_site.length != 0) temp_sites.push(temp_site);
        
        this.sites.set(k, temp_sites);

        if(jumpToLast && this.cur_category == k){
          this.cur_index = temp_sites.length - 1
        }
      })
    })
  }

  changeEditItem(id : number) : void{
    this.cur_edit = id;
  }

  changeSite(offset : number) : void {
    this.cur_index += offset;    
  }

  onCategoryChange() : void{
    this.cur_index = 0    
  }

  regenTexturePack() : void{
    this.api.regenResourcepack().subscribe((dump) => {
      this.loadRPVersion();
      this.loadElements();
    })
  }

  deleteItem(item : Item) : void {
    this.api.deleteItem(item.ID).subscribe((dump) => {
      this.loadElements();
    })
  }

  getItemByID(id : number) : Item{
    return this.items.find((item) => item.ID == id)!
  }

  createItem() : void {
    this.api.createItem(this._temp_name, this.cur_category).subscribe((dump) => {
      this.loadElements(true)
    })
  }
}
