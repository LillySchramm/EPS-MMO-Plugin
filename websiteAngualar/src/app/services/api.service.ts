import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { getAllNPCResponse, getAllStaticEffectsResponse, loginResponse, verifyResponse } from 'src/common/generalTypes';
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