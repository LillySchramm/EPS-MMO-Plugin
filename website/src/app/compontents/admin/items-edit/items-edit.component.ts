import { Component, OnInit, Input } from '@angular/core';
import { APIService } from 'src/app/services/api.service';
import { Item } from 'src/common/itemType';
import { ItemsOverviewComponent } from '../items-overview/items-overview.component';

@Component({
  selector: 'app-items-edit',
  templateUrl: './items-edit.component.html',
  styleUrls: ['./items-edit.component.css']
})
export class ItemsEditComponent implements OnInit {

  @Input() item : Item;

  ItemOverviewComponent = ItemsOverviewComponent

  itemType : string = ""

  constructor(private api:APIService) {
    this.item = {
      DATA: "",
      ICON:"",
      ID:0,
      NAME:""
    }
  }

  //Placeholder
  deconstructData() : void {
    this.itemType = this.item.DATA
  }

  //Placeholder
  reconstructData() : void {
    this.item.DATA = this.itemType
  }

  //Placeholder
  onTypeChange() : void {

  }

  ngOnInit(): void {
    this.deconstructData()
  }

  onSubmit() : void {
    this.reconstructData()
    this.api.saveItem(this.item);
  }

  onIconChange(event : any) : void{
    
    if(event.target.files){
      const file = event.target.files[0];
      
      const reader = new FileReader();
      reader.readAsDataURL(file)
      reader.onload = () => {
        this.item.ICON = reader.result!.toString().split(",")[1]
      };
    }
  }
}
