import { Component, OnInit } from '@angular/core';
import { User } from '../models/user.model';
import { Error } from '../models/error.model';
import { Router } from '@angular/router';
import { LoginService } from '../service/login.service';


@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
})
export class FormComponent implements OnInit {
  user: User;
  er: Error;
  message: string = null;
  errStatus: boolean = false;
  formStatus: boolean = false;
  serverStatus: boolean = false;
  constructor(private router: Router, private loginService: LoginService) { }

  ngOnInit() {
  }
  save(loginForm) {
    console.log("After submitting", loginForm.value);
    let object = loginForm.value;
    console.log("Object", object)
    this.user = loginForm.value;
    console.log("User", this.user);
    this.loginService.loginUser(this.user)
      .subscribe(
        data => {
          if (data["status"] == "SUCCESS") {
            this.router.navigateByUrl('/home');

          } else {
            this.errStatus = true;
            this.er = data["message"];
            console.log("Err", this.er);
          }
        }
        , error => {
          this.router.navigateByUrl('/home');
          // this.serverStatus = true;
          // console.log("error Status",error.status)
          // if (error.status == 500 || error.status == 0) {
          //   this.message = 'Server is down';
          //   console.log("this.er inside if", this.message);
          // }
        });

  }
}
