import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Data } from '../student/student.component';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private http: HttpClient) {

  }
  private npmStartServicePath = './MyApp'; // 用proxy相對路徑
  private WEB_SERVICE_PATH = environment.production
    ? environment.API_URL
    : this.npmStartServicePath; // setting webservice path
  private JWT_TOKEN = '';

  /**
  * 發出的Request僅需帶入Header時使用
  * @param apiName API名稱
  * @param body RequestBody
  * @returns
  */
  public post<T>(apiName: string, body: {},options?:{}) {
    return this.http.post<T>(
      this.WEB_SERVICE_PATH + apiName,
      body,
      this.generateRequestHeader(options)
    );
    // .pipe(catchError((err) => {
    //   alert(err);
    //   // this.navigateToIndex();
    //   throw new Error(`發生網路問題 Error Code: ${err}`);
    // }));
  }

  /**
   * 發出的Request僅需帶入Header時使用
   * @param apiName API名稱
   * @returns
   */
  public get<T>(apiName: string,options?:{}) {
    return this.http.get<T>(
      this.WEB_SERVICE_PATH + apiName,
      this.generateRequestHeader(options)
    );
  }

  public getJwtToken(): string {
    if (this.JWT_TOKEN) {
      return this.JWT_TOKEN;
    }
    const token: string | null = sessionStorage.getItem('token');
    return token ?? '';
  }

  /**回傳{ header : HttpHeader }*/
  private generateRequestHeader(options?:{}) {
    const token = this.getJwtToken();
    return options
    ? Object.assign(options, {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', token)
    }):{
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
        .set('Authorization', token)
    };
  }

  public goToLoginPage() {
    window.location.href = `${this.WEB_SERVICE_PATH}/login`;
  }
}


