import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBioProfile } from '../bio-profile.model';
import { BioProfileService } from '../service/bio-profile.service';

@Component({
  templateUrl: './bio-profile-delete-dialog.component.html',
})
export class BioProfileDeleteDialogComponent {
  bioProfile?: IBioProfile;

  constructor(protected bioProfileService: BioProfileService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bioProfileService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
