import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { APIService } from 'src/app/services/api.service';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent implements OnInit {

  wrong : boolean = false;
  name : string = "";
  password : string = ""

  constructor(private api:APIService, private cookies:CookieService) { }

  ngOnInit(): void {
  }
 
  onSubmit() : void{
    if(this.name != "" && this.password != ""){
      this.api.login(this.name, this.password).subscribe((ret) => {
        if(ret.successfull){
          this.cookies.set('session', ret.session);
        }else{
          this.wrong = true;
        }
      })
    }
  }

}
