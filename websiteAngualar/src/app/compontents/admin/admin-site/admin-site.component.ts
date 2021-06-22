import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { APIService } from 'src/app/services/api.service';

@Component({
  selector: 'app-admin-site',
  templateUrl: './admin-site.component.html',
  styleUrls: ['./admin-site.component.css']
})
export class AdminSiteComponent implements OnInit {

  public static SessionKey = ""

  loggedIn : boolean = false;
  currentSite : string = "npc";

  constructor(private cookies:CookieService, private api:APIService) { }

  ngOnInit(): void {
    this.checkKey()
  }

  checkKey() : void{
    if(AdminSiteComponent.SessionKey === ""){
      if(this.cookies.check('session')){
        AdminSiteComponent.SessionKey = this.cookies.get('session');
      }
      window.setTimeout(() => {this.checkKey()}, 100)
    }else{
      this.api.verify(AdminSiteComponent.SessionKey).subscribe((d) => {
        if(d.verified){
          this.loggedIn = true;
          setTimeout(() => {this.checkKey()}, 10000);
        }else{
          AdminSiteComponent.SessionKey = ""
          if(this.cookies.check('session')){
            this.cookies.delete('session')
          }
          this.loggedIn = false;
          setTimeout(() => {this.checkKey()}, 100);
        }
      })
    }
  }

}
