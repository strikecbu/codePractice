import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Post } from "./post.model";
import { map } from "rxjs/operators";
import { Observable, Subject } from "rxjs";

@Injectable()
export class PostService {
  constructor(private http: HttpClient) {}
  error = new Subject<string>();
  private apiUrl =
    "https://angular-http2-3d5ab-default-rtdb.asia-southeast1.firebasedatabase.app/posts.json";

  addPostData(title: string, content: string) {
    const postData: Post = { title: title, content: content };
    this.http
      .post<{ name: string }>(this.apiUrl, postData)
      .subscribe((responseData) => {
        console.log(responseData);
      }, error => {
          this.error.next(error.message);
      });
  }

  fetchPostData(): Observable<Post[]> {
    return this.http.get<{ [key: string]: Post }>(this.apiUrl).pipe(
      map((responseData) => {
        const posts: Post[] = [];
        for (let key in responseData) {
          if (responseData.hasOwnProperty(key))
            posts.push({ ...responseData[key], id: key });
        }
        return posts;
      })
    );
  }

  deletePost() {
    return this.http.delete(this.apiUrl);
  }
}
