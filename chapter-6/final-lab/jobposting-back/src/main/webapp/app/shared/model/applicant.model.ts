import { IApplication } from 'app/shared/model/application.model';

export interface IApplicant {
  applicantId?: number;
  name?: string | null;
  email?: string | null;
  phone?: string | null;
  applications?: IApplication[] | null;
}

export const defaultValue: Readonly<IApplicant> = {};
