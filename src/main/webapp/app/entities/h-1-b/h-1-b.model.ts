import * as dayjs from 'dayjs';
import { GenderType } from 'app/entities/enumerations/gender-type.model';
import { CategoryType } from 'app/entities/enumerations/category-type.model';

export interface IH1B {
  id?: number;
  userId?: string | null;
  firstName?: string | null;
  middleName?: string | null;
  lastName?: string | null;
  dateOfBirth?: dayjs.Dayjs | null;
  countryOfBirth?: string | null;
  countryOfCitizenShip?: string | null;
  passportNumber?: string | null;
  gender?: GenderType | null;
  category?: CategoryType | null;
  email?: string | null;
  currentAddress?: string | null;
  phoneNumber?: string | null;
  currentVisaStatus?: string | null;
  referedBy?: string | null;
  passportFrontPage?: string | null;
  passportBackPage?: string | null;
}

export class H1B implements IH1B {
  constructor(
    public id?: number,
    public userId?: string | null,
    public firstName?: string | null,
    public middleName?: string | null,
    public lastName?: string | null,
    public dateOfBirth?: dayjs.Dayjs | null,
    public countryOfBirth?: string | null,
    public countryOfCitizenShip?: string | null,
    public passportNumber?: string | null,
    public gender?: GenderType | null,
    public category?: CategoryType | null,
    public email?: string | null,
    public currentAddress?: string | null,
    public phoneNumber?: string | null,
    public currentVisaStatus?: string | null,
    public referedBy?: string | null,
    public passportFrontPage?: string | null,
    public passportBackPage?: string | null
  ) {}
}

export function getH1BIdentifier(h1B: IH1B): number | undefined {
  return h1B.id;
}
