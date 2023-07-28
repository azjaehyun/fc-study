import { IJobPosting } from 'app/shared/model/job-posting.model';

export interface ICompany {
  companyId?: number;
  name?: string | null;
  address?: string | null;
  jobPostings?: IJobPosting[] | null;
}

export const defaultValue: Readonly<ICompany> = {};
