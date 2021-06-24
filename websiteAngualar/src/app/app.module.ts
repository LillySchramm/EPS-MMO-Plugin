import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'
import { FormsModule } from '@angular/forms';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AdminSiteComponent } from './compontents/admin/admin-site/admin-site.component';
import { AdminLoginComponent } from './compontents/admin/admin-login/admin-login.component';
import { NpcOverviewComponent } from './compontents/admin/npc-overview/npc-overview.component';
import { PosHelperComponent } from './compontents/helpers/pos-helper/pos-helper.component';
import { SkinHelperComponent } from './compontents/helpers/skin-helper/skin-helper.component';
import { NpcEditComponent } from './compontents/admin/npc-edit/npc-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminSiteComponent,
    AdminLoginComponent,
    NpcOverviewComponent,
    PosHelperComponent,
    SkinHelperComponent,
    NpcEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
