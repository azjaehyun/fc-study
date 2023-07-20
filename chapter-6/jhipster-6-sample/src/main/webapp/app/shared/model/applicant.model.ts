import { IResume } from 'app/shared/model/resume.model';
import { IApplication } from 'app/shared/model/application.model';

export interface IApplicant {
  applicantId?: number;
  name?: string | null;
  email?: string | null;
  phone?: string | null;
  resumes?: IResume[] | null;
  applications?: IApplication[] | null;
}

export const defaultValue: Readonly<IApplicant> = {};
