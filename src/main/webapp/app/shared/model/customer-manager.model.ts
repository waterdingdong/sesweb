import { IProjects } from 'app/shared/model/projects.model';

export interface ICustomerManager {
  id?: number;
  customerName?: string;
  name?: string;
  email?: string | null;
  phoneName?: string | null;
  projects?: IProjects[] | null;
}

export const defaultValue: Readonly<ICustomerManager> = {};
