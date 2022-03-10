import {Component, Input, OnInit} from '@angular/core';

import {ServersService} from '../servers.service';
import {ActivatedRoute, Router} from '@angular/router';
import {CanDeactivateComponent} from '../../can-deactivate.guard';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-edit-server',
  templateUrl: './edit-server.component.html',
  styleUrls: ['./edit-server.component.css']
})
export class EditServerComponent implements OnInit, CanDeactivateComponent {
  @Input() server: { id: number, name: string, status: string };
  serverName = '';
  serverStatus = '';
  allowEdit = false;
  hasChange = false;

  constructor(private serversService: ServersService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    // this.server = this.serversService.getServer(1);
    this.allowEdit = this.route.snapshot.queryParams['editAccess'] === '1';
    const serverId: number = +this.route.snapshot.params['id'];
    this.server = this.serversService.getServer(serverId);
    this.serverName = this.server.name;
    this.serverStatus = this.server.status;
  }

  onUpdateServer() {
    this.serversService.updateServer(this.server.id, {name: this.serverName, status: this.serverStatus});
    this.hasChange = false;
    this.router.navigate(['../'], {relativeTo: this.route, queryParamsHandling: 'preserve'});
  }

  canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
    if(this.server.name !== this.serverName || this.server.status !== this.serverStatus) {
      this.hasChange = true;
    }
    if(this.hasChange) {
      return confirm('Are You sure want to leave without save?')
    }
    return true;
  }

}
