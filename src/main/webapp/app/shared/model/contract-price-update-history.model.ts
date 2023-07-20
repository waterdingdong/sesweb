import dayjs from 'dayjs';
import { IContracts } from 'app/shared/model/contracts.model';

export interface IContractPriceUpdateHistory {
  id?: number;
  updateDate?: string | null;
  beforePrice?: number | null;
  afterPrice?: number | null;
  contract?: IContracts | null;
}

export const defaultValue: Readonly<IContractPriceUpdateHistory> = {};
