import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private baseUrl = "http://hddravithadak:8086/irmweb/authenticate";
  constructor(private http: HttpClient) { }
  loginUser(user: Object): Observable<Object> {
    sessionStorage.setItem('userid', user["userid"]);
    return this.http.post(`${this.baseUrl}`, user)
  }
  isUserLoggedIn() {
    let user = sessionStorage.getItem('userid')
    console.log(!(user === null))
    return !(user === null)
  }

  logOut() {
    sessionStorage.removeItem('userid')
  }
}
