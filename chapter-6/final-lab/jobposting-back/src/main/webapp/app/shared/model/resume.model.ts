import dayjs from 'dayjs';
import { IApplication } from 'app/shared/model/application.model';

export interface IResume {
  resumeId?: number;
  title?: string | null;
  content?: string | null;
  submittedDate?: string | null;
  applications?: IApplication[] | null;
}

export const defaultValue: Readonly<IResume> = {};
