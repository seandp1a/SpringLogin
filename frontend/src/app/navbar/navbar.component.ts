import { LoginService } from './../service/login.service';
import { Component, OnInit } from '@angular/core';
import { BackendService } from '../service/backend.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  title = 'tailwind-test';
  modelState = 'off';
  public isLogin :boolean = false;

  constructor(
    private loginSvc:LoginService
  ) {

  }
  darkMode(mode:string){
    // console.log(document.documentElement);
    if(mode==='on'){
      document.documentElement.classList.add('dark');
    }else{
      document.documentElement.classList.remove('dark');
    }
  }
  public logout(){
    this.loginSvc.logout();
  }

  ngOnInit(): void {
    this.loginSvc.isLogin.subscribe(res=>{
      this.isLogin = res;
    });
  }

}
