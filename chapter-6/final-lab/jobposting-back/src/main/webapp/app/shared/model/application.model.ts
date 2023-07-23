import dayjs from 'dayjs';
import { IResume } from 'app/shared/model/resume.model';
import { IJobPosting } from 'app/shared/model/job-posting.model';
import { IApplicant } from 'app/shared/model/applicant.model';

export interface IApplication {
  applicationId?: number;
  applicationDate?: string | null;
  resume?: IResume | null;
  jobPosting?: IJobPosting | null;
  applicant?: IApplicant | null;
}

export const defaultValue: Readonly<IApplication> = {};
