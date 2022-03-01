import { LoginService } from './../service/login.service';
import { BackendService } from './../service/backend.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  userName!: String;
  password!: String;

  constructor(private loginSvc:LoginService) { }

  doLogin() {
    // this.backendSvc.getRoot().subscribe(res=>{
    //   console.log(res);
    // })
    if (!this.userName || !this.password) return;
    const body = {
      username: this.userName,
      password: this.password
    };
  }

  ngOnInit(): void {
  }



}
