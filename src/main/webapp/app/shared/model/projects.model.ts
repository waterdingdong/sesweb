import { IContracts } from 'app/shared/model/contracts.model';
import { ICustomerManager } from 'app/shared/model/customer-manager.model';

export interface IProjects {
  id?: number;
  name?: string | null;
  contracts?: IContracts[] | null;
  customerManager?: ICustomerManager | null;
}

export const defaultValue: Readonly<IProjects> = {};
