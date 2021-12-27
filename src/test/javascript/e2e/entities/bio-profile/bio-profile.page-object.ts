import { element, by, ElementFinder } from 'protractor';

export class BioProfileComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-bio-profile div table .btn-danger'));
  title = element.all(by.css('jhi-bio-profile div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class BioProfileUpdatePage {
  pageTitle = element(by.id('jhi-bio-profile-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  userNameInput = element(by.id('field_userName'));
  userIdInput = element(by.id('field_userId'));
  memberIdInput = element(by.id('field_memberId'));
  firstNameInput = element(by.id('field_firstName'));
  lastNameInput = element(by.id('field_lastName'));
  dobInput = element(by.id('field_dob'));
  genderInput = element(by.id('field_gender'));
  imageUrlInput = element(by.id('field_imageUrl'));
  titleInput = element(by.id('field_title'));
  summaryInput = element(by.id('field_summary'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setUserNameInput(userName: string): Promise<void> {
    await this.userNameInput.sendKeys(userName);
  }

  async getUserNameInput(): Promise<string> {
    return await this.userNameInput.getAttribute('value');
  }

  async setUserIdInput(userId: string): Promise<void> {
    await this.userIdInput.sendKeys(userId);
  }

  async getUserIdInput(): Promise<string> {
    return await this.userIdInput.getAttribute('value');
  }

  async setMemberIdInput(memberId: string): Promise<void> {
    await this.memberIdInput.sendKeys(memberId);
  }

  async getMemberIdInput(): Promise<string> {
    return await this.memberIdInput.getAttribute('value');
  }

  async setFirstNameInput(firstName: string): Promise<void> {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput(): Promise<string> {
    return await this.firstNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName: string): Promise<void> {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput(): Promise<string> {
    return await this.lastNameInput.getAttribute('value');
  }

  async setDobInput(dob: string): Promise<void> {
    await this.dobInput.sendKeys(dob);
  }

  async getDobInput(): Promise<string> {
    return await this.dobInput.getAttribute('value');
  }

  async setGenderInput(gender: string): Promise<void> {
    await this.genderInput.sendKeys(gender);
  }

  async getGenderInput(): Promise<string> {
    return await this.genderInput.getAttribute('value');
  }

  async setImageUrlInput(imageUrl: string): Promise<void> {
    await this.imageUrlInput.sendKeys(imageUrl);
  }

  async getImageUrlInput(): Promise<string> {
    return await this.imageUrlInput.getAttribute('value');
  }

  async setTitleInput(title: string): Promise<void> {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput(): Promise<string> {
    return await this.titleInput.getAttribute('value');
  }

  async setSummaryInput(summary: string): Promise<void> {
    await this.summaryInput.sendKeys(summary);
  }

  async getSummaryInput(): Promise<string> {
    return await this.summaryInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class BioProfileDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-bioProfile-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-bioProfile'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
