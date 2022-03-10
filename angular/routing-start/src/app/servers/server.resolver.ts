import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from "@angular/router";
import { Observable, Subject } from "rxjs";
import { Injectable } from "@angular/core";
import { ServersService } from "./servers.service";

interface Server {
  id: number;
  name: string;
  status: string;
}

@Injectable()
export class ServerResolver implements Resolve<Server> {
  constructor(private serverService: ServersService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<Server> | Promise<Server> | Server {
    let server = this.serverService.getServer(+route.params["id"]);
    // return server;
    // return new Promise<Server>((resolve1, reject) => {
    //   resolve1(server);
    // });

    return Observable.create(observer => {
      observer.next(server)
      observer.complete();
    });
    // let subject = new Subject<Server>();
    // setTimeout(() => {
    //   subject.next(server);
    // }, 800);
    // return subject;
  }
}
