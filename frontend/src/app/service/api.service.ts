import { BackendService } from './backend.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(
    private backendSvc:BackendService
  ) { }

  public getAuthenticatedUserInfo(){
    return this.backendSvc.get<any>('/getAuthenticatedUserInfo');
  }

  public hasRootAuthority(){
    return this.backendSvc.get<string>('/hasRootAuthority',{responseType: 'text'});
  }
  public hasRootAndUserAuthority(){
    return this.backendSvc.get<string>('/hasRootAndUserAuthority',{responseType: 'text'});
  }
  public hasRootRole(){
    return this.backendSvc.get<string>('/hasRootRole',{responseType: 'text'});
  }
  public hasAnyRole(){
    return this.backendSvc.get<string>('/hasAnyRole',{responseType: 'text'});
  }


}
