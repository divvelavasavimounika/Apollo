import { Input, Component } from '@angular/core';
import { ALERTS } from './alert.model';


@Component({
  selector: 'app-alert',
  templateUrl: './alert.html',
  styleUrls: ['./alert.css']
})
export class AlertComponent {

  ngInit() {

  }
  alerts = ALERTS;
  constructor() {

    console.log(this.alerts);


  }


}
