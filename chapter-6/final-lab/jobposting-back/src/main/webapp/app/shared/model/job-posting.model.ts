import dayjs from 'dayjs';
import { IApplication } from 'app/shared/model/application.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IJobPosting {
  jobId?: number;
  title?: string | null;
  description?: string | null;
  location?: string | null;
  postedDate?: string | null;
  applications?: IApplication[] | null;
  company?: ICompany | null;
}

export const defaultValue: Readonly<IJobPosting> = {};
