import { BackendService } from './backend.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private backendSvc:BackendService) { }


  public logout(){
    this.backendSvc.get('/api/logout').subscribe(res=>{
      console.log(res);
      this.backendSvc.goToLoginPage();
    });
  }
}
