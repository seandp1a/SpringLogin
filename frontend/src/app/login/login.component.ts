import { LoginService } from './../service/login.service';
import { BackendService } from './../service/backend.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  userName!: string;
  password!: string;

  constructor(private loginSvc:LoginService) { }

  doLogin() {
    if (!this.userName || !this.password) return;
    this.loginSvc.login(this.userName,this.password);
  }

  ngOnInit(): void {
  }



}
