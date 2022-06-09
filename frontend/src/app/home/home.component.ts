import { LoginService } from './../service/login.service';
import { ApiService } from './../service/api.service';
import { BackendService } from './../service/backend.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(
    public apiSvc: ApiService,
    private loginSVc: LoginService
  ) { }
  public userName: string = '';
  public userAccess: string = '';
  public apiResult: string = '';
  public isLogin :boolean = false;

  private replaceApiResult(res: string) {
    this.apiResult = res;
  }

  public hasRootAuthority() {
    this.apiSvc.hasRootAuthority().subscribe((res) => {
      this.replaceApiResult(res);
    }, (err) => {
      this.replaceApiResult(JSON.parse(err.error).message);
    });
  }

  public hasRootAndUserAuthority() {
    this.apiSvc.hasRootAndUserAuthority().subscribe((res) => {
      this.replaceApiResult(res);
    }, (err) => {
      this.replaceApiResult(JSON.parse(err.error).message);
    });
  }

  public hasAnyRole() {
    this.apiSvc.hasAnyRole().subscribe((res) => {
      this.replaceApiResult(res);
    }, (err) => {
      this.replaceApiResult(JSON.parse(err.error).message);
    });
  }

  public hasRootRole() {
    this.apiSvc.hasRootRole().subscribe((res) => {
      this.replaceApiResult(res);
    }, (err) => {
      this.replaceApiResult(JSON.parse(err.error).message);
    });
  }

  ngOnInit(): void {
    this.apiSvc.getAuthenticatedUserInfo().subscribe((res) => {
      console.log(res);
      this.loginSVc.isLogin.next(true);
      this.userName = res.name;
      this.userAccess = res.access;
    }, (err) => {
      this.loginSVc.isLogin.next(false);
      this.replaceApiResult(err.error);
    });
    this.loginSVc.isLogin.subscribe(res => {
      this.isLogin = res;
      if (!res) {
        this.userName = "";
        this.userAccess = "";
      }
    });

  }

}
