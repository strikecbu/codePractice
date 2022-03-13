import { HttpClient } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { Post } from "./post.model";
import { map } from "rxjs/operators";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent implements OnInit {
  loadedPosts: Post[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {}

  onCreatePost(postData: Post) {
    // Send Http request
    this.http
      .post<{ name: string }>(
        "https://angulor-http-default-rtdb.firebaseio.com/posts.json",
        postData
      )
      .subscribe((responseData) => {
        console.log(responseData);
        this.onFetchPosts();
      });
  }

  onFetchPosts() {
    // Send Http request
    this.http
      .get<{ [key: string]: Post }>(
        "https://angulor-http-default-rtdb.firebaseio.com/posts.json"
      )
      .pipe(
        map((responseData) => {
          const posts: Post[] = [];
          for (let key in responseData) {
            if (responseData.hasOwnProperty(key))
              posts.push({ ...responseData[key], id: key });
          }
          return posts;
        })
      )
      .subscribe((posts) => {
        this.loadedPosts = posts;
      });
  }

  onClearPosts() {
    // Send Http request
  }
}
