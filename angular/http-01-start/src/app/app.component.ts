import { HttpClient } from "@angular/common/http";
import { Component, OnDestroy, OnInit } from "@angular/core";
import { Post } from "./post.model";
import { PostService } from "./post.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent implements OnInit, OnDestroy {
  constructor(private http: HttpClient, private postService: PostService) {}
  
  loadedPosts: Post[] = [];
  isFetching = false;
  error: string = null;

  errorSub = this.postService.error.subscribe((error) => {
    this.error = error;
  });

  ngOnInit() {
    this.onFetchPosts();
  }
  ngOnDestroy(): void {
    this.errorSub.unsubscribe();
  }

  onCreatePost(postData: Post) {
    // Send Http request
    this.postService.addPostData(postData.title, postData.content);
  }

  onFetchPosts() {
    // Send Http request
    this.isFetching = true;
    this.postService.fetchPostData().subscribe(
      (loadPosts) => {
        this.loadedPosts = loadPosts;
        this.isFetching = false;
      },
      (error) => {
        this.error = error.message;
        this.isFetching = false;
      }
    );
  }

  onClearPosts() {
    // Send Http request
    this.postService.deletePost().subscribe(
      (data) => {
        console.log(data);
        this.loadedPosts = [];
      },
      (error) => {
        this.error = error.message;
      }
    );
  }

  onErrorOk() {
    this.error = null;
  }
}
