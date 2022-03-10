import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  serverElements = [];


  ngOnInit(): void {
  }

  addServer($event): void {
    this.serverElements.push($event);
  }

}
