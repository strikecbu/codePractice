import {Component, Input, OnInit} from '@angular/core';

import {ServersService} from '../servers.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-server',
  templateUrl: './server.component.html',
  styleUrls: ['./server.component.css']
})
export class ServerComponent implements OnInit {
  @Input() server: { id: number, name: string, status: string };
  queryParamsSubscription: Subscription;

  constructor(private serversService: ServersService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.data.subscribe(data => {
      this.server = data['server'];
    })
    // this.queryParamsSubscription = this.route.params.subscribe(params => {
    //   if (params['id']) {
    //     this.server = this.serversService.getServer(+params['id']);
    //   }
    // });
    // this.queryParamsSubscription = this.route.params.subscribe(params => {
    //   this.server = this.serversService.getServer(Number.parseInt(params['id'], 10));
    // });
  }

  onUpdate() {
    this.router.navigate(['edit'], {relativeTo: this.route, queryParamsHandling: 'preserve'});
  }
}
