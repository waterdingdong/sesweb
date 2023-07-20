import { IContracts } from 'app/shared/model/contracts.model';

export interface IPartnerManager {
  id?: number;
  name?: string;
  companyName?: string;
  email?: string | null;
  phoneNumber?: string | null;
  wechatId?: string | null;
  contracts?: IContracts[] | null;
}

export const defaultValue: Readonly<IPartnerManager> = {};
