import dayjs from 'dayjs';
import { IApplication } from 'app/shared/model/application.model';

export interface IJobPosting {
  jobId?: number;
  title?: string | null;
  description?: string | null;
  location?: string | null;
  postedDate?: string | null;
  applications?: IApplication[] | null;
}

export const defaultValue: Readonly<IJobPosting> = {};
