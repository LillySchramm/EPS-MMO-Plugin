import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { getAllItemsResponse, getAllNPCResponse, getAllStaticEffectsResponse, getResourcepackVersionResponse, itemUpdateRequest, loginResponse, verifyResponse } from 'src/common/generalTypes';
import { Item } from 'src/common/itemType';
import { AdminSiteComponent } from '../compontents/admin/admin-site/admin-site.component';

@Injectable({
  providedIn: 'root'
})
export class APIService {
  public static apiUrl = 'http://mine:10100/'

  constructor(private http: HttpClient) { }

  public login(name: string, password: string): Observable<loginResponse> {
    return this.http.get<loginResponse>(APIService.apiUrl + "login/" + name + "/" + password)
  }

  public verify(key: string): Observable<verifyResponse> {
    return this.http.get<verifyResponse>(APIService.apiUrl + "admin/" + key)
  }

  public getAllNPC(): Observable<getAllNPCResponse> {
    return this.http.get<getAllNPCResponse>(APIService.apiUrl + "admin/" + AdminSiteComponent.SessionKey +
      "/npc/getall")
  }

  public getAllItems(category : string): Observable<getAllItemsResponse> {
    return this.http.get<getAllItemsResponse>(APIService.apiUrl + "admin/" + AdminSiteComponent.SessionKey +
      "/item/getall/" + category)
  }

  public getAllStaticEffects(): Observable<getAllStaticEffectsResponse> {
    return this.http.get<getAllStaticEffectsResponse>(APIService.apiUrl + "admin/" + AdminSiteComponent.SessionKey +
      "/staticeffects/getall")
  }

  public saveNPC(id: number, attr: string, value: string): void {
    this.http.get(APIService.apiUrl + "admin/" + AdminSiteComponent.SessionKey +
      "/npc/set/" + id + "/" + attr + "/" + this.hexEncode(value)).subscribe(() => {})
  }

  public saveStaticEffect(id: number, data: string): void {
    this.http.get(APIService.apiUrl + "admin/" + AdminSiteComponent.SessionKey +
      "/staticeffects/set/" + id + "/DATA/" + this.hexEncode(data)).subscribe(() => {})
  }

  public getResourcepackVersion() : Observable<getResourcepackVersionResponse> {
    return this.http.get<getResourcepackVersionResponse>(APIService.apiUrl + "resourcepack/version")
  }

  public regenResourcepack() : Observable<verifyResponse> {
    return this.http.get<verifyResponse>(APIService.apiUrl + "admin/" + AdminSiteComponent.SessionKey +
    "/genResourcePack")
  }

  public deleteItem(id: number) : Observable<verifyResponse> {
    return this.http.get<verifyResponse>(APIService.apiUrl + "admin/" + AdminSiteComponent.SessionKey +
    "/item/delete/" + id);
  }

  public createItem(name : string, category : string) : Observable<verifyResponse>{
    return this.http.get<verifyResponse>(APIService.apiUrl + "admin/" + AdminSiteComponent.SessionKey +
    "/item/new/" + name + "/" + category)
  }

  public saveItem(item:Item) : void{
    let req : itemUpdateRequest = {
      data:item.DATA,
      base:item.ICON,
      name:item.NAME
    }

    this.http.post(APIService.apiUrl + "admin/" + AdminSiteComponent.SessionKey +
      "/item/icon/" + item.ID, req).subscribe((dump) => {})
  }


  hexEncode(str: string) {
    var hex, i;

    var result = "";
    for (i = 0; i < str.length; i++) {
      hex = str.charCodeAt(i).toString(16);
      result += ("000" + hex).slice(-4);
    }

    return result
  }
}