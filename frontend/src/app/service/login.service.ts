import { BackendService } from './backend.service';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private backendSvc:BackendService,
    private route:Router
    ) { }

  public isLogin = new BehaviorSubject(false);


  public login(account:string,password:string){
    const body = {
      account:account,
      password:password
    }
    this.backendSvc.postWithoutFiltError<LoginResponse>('/api/login',body).subscribe((res)=>{
      this.isLogin.next(true);
      this.route.navigate(['/home']);
    });
  }

  public logout(){
    this.backendSvc.get<{status:string,message:string}>('/api/logout').subscribe(res=>{
      if(res.status==="200"){
        alert(res.message);
        this.isLogin.next(false);
        this.route.navigate(['home'])
      }
    });
  }
}
export interface LoginResponse{
  account:string,
  message:string,
  authority:string
}
