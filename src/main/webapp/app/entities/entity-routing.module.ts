import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'login-profile',
        data: { pageTitle: 'h1BRegisterApplicationApp.loginProfile.home.title' },
        loadChildren: () => import('./login-profile/login-profile.module').then(m => m.LoginProfileModule),
      },
      {
        path: 'bio-profile',
        data: { pageTitle: 'h1BRegisterApplicationApp.bioProfile.home.title' },
        loadChildren: () => import('./bio-profile/bio-profile.module').then(m => m.BioProfileModule),
      },
      {
        path: 'h-1-b',
        data: { pageTitle: 'h1BRegisterApplicationApp.h1B.home.title' },
        loadChildren: () => import('./h-1-b/h-1-b.module').then(m => m.H1BModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
