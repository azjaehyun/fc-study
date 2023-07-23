import dayjs from 'dayjs';
import { IResume } from 'app/shared/model/resume.model';

export interface IApplication {
  applicationId?: number;
  applicationDate?: string | null;
  resume?: IResume | null;
}

export const defaultValue: Readonly<IApplication> = {};
