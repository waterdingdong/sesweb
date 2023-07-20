import dayjs from 'dayjs';
import { ISalesAmount } from 'app/shared/model/sales-amount.model';
import { IContractPriceUpdateHistory } from 'app/shared/model/contract-price-update-history.model';
import { IProjects } from 'app/shared/model/projects.model';
import { IPartnerManager } from 'app/shared/model/partner-manager.model';

export interface IContracts {
  id?: number;
  lastName?: string;
  firstName?: string;
  ecnId?: string | null;
  manager?: string | null;
  managerEcnId?: string | null;
  contractStartDate?: string | null;
  contractEndDate?: string | null;
  orderUnitPrice?: number;
  maximumOrderSettlementCondition?: number | null;
  minimumOrderSettlementCondition?: number | null;
  orderDeductionUnitPrice?: number | null;
  orderSurchargeUnitPrice?: number | null;
  purchaseUnitPrice?: number | null;
  maximumPurchaseSettlementCondition?: number | null;
  minimumPurchaseSettlementCondition?: number | null;
  purchaseDeductionUnitPrice?: number | null;
  purchaseSurchargeUnitPrice?: number | null;
  salesAmounts?: ISalesAmount[] | null;
  priceUpdateHistories?: IContractPriceUpdateHistory[] | null;
  project?: IProjects | null;
  partnerManager?: IPartnerManager | null;
}

export const defaultValue: Readonly<IContracts> = {};
