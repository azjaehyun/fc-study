import { IResume } from 'app/shared/model/resume.model';

export interface IApplicant {
  applicantId?: number;
  name?: string | null;
  email?: string | null;
  phone?: string | null;
  resumes?: IResume[] | null;
}

export const defaultValue: Readonly<IApplicant> = {};
