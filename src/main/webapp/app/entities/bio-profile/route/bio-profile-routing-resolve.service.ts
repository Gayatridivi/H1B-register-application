import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBioProfile, BioProfile } from '../bio-profile.model';
import { BioProfileService } from '../service/bio-profile.service';

@Injectable({ providedIn: 'root' })
export class BioProfileRoutingResolveService implements Resolve<IBioProfile> {
  constructor(protected service: BioProfileService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBioProfile> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bioProfile: HttpResponse<BioProfile>) => {
          if (bioProfile.body) {
            return of(bioProfile.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BioProfile());
  }
}
