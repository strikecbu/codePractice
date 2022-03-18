import { Component, OnInit } from '@angular/core';
import { CourseStoreService } from './store/course-store.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  constructor(private courseStore: CourseStoreService) {

  }
  ngOnInit(): void {
    this.courseStore.getCourseData();
  }
  title = 'app';
}
