import { IContracts } from 'app/shared/model/contracts.model';

export interface ISalesAmount {
  id?: number;
  salesYm?: string | null;
  workTime?: number | null;
  billingAmount?: number | null;
  contract?: IContracts | null;
}

export const defaultValue: Readonly<ISalesAmount> = {};
