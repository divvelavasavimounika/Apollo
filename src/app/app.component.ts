import { Component } from '@angular/core';
import { Loaders } from '../app/loader.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor() { 
    console.log(this.loaders)
  }

  loaders=Loaders;
  

}
