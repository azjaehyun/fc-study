import dayjs from 'dayjs';
import { IApplication } from 'app/shared/model/application.model';
import { IApplicant } from 'app/shared/model/applicant.model';

export interface IResume {
  resumeId?: number;
  title?: string | null;
  content?: string | null;
  submittedDate?: string | null;
  applications?: IApplication[] | null;
  applicant?: IApplicant | null;
}

export const defaultValue: Readonly<IResume> = {};
