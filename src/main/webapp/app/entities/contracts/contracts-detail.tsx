import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contracts.reducer';

export const ContractsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const contractsEntity = useAppSelector(state => state.contracts.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contractsDetailsHeading">
          <Translate contentKey="seswebApp.contracts.detail.title">Contracts</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{contractsEntity.id}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="seswebApp.contracts.lastName">Last Name</Translate>
            </span>
            <UncontrolledTooltip target="lastName">
              <Translate contentKey="seswebApp.contracts.help.lastName" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.lastName}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="seswebApp.contracts.firstName">First Name</Translate>
            </span>
            <UncontrolledTooltip target="firstName">
              <Translate contentKey="seswebApp.contracts.help.firstName" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.firstName}</dd>
          <dt>
            <span id="ecnId">
              <Translate contentKey="seswebApp.contracts.ecnId">Ecn Id</Translate>
            </span>
            <UncontrolledTooltip target="ecnId">
              <Translate contentKey="seswebApp.contracts.help.ecnId" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.ecnId}</dd>
          <dt>
            <span id="manager">
              <Translate contentKey="seswebApp.contracts.manager">Manager</Translate>
            </span>
            <UncontrolledTooltip target="manager">
              <Translate contentKey="seswebApp.contracts.help.manager" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.manager}</dd>
          <dt>
            <span id="managerEcnId">
              <Translate contentKey="seswebApp.contracts.managerEcnId">Manager Ecn Id</Translate>
            </span>
            <UncontrolledTooltip target="managerEcnId">
              <Translate contentKey="seswebApp.contracts.help.managerEcnId" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.managerEcnId}</dd>
          <dt>
            <span id="contractStartDate">
              <Translate contentKey="seswebApp.contracts.contractStartDate">Contract Start Date</Translate>
            </span>
            <UncontrolledTooltip target="contractStartDate">
              <Translate contentKey="seswebApp.contracts.help.contractStartDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {contractsEntity.contractStartDate ? (
              <TextFormat value={contractsEntity.contractStartDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="contractEndDate">
              <Translate contentKey="seswebApp.contracts.contractEndDate">Contract End Date</Translate>
            </span>
            <UncontrolledTooltip target="contractEndDate">
              <Translate contentKey="seswebApp.contracts.help.contractEndDate" />
            </UncontrolledTooltip>
          </dt>
          <dd>
            {contractsEntity.contractEndDate ? (
              <TextFormat value={contractsEntity.contractEndDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="orderUnitPrice">
              <Translate contentKey="seswebApp.contracts.orderUnitPrice">Order Unit Price</Translate>
            </span>
            <UncontrolledTooltip target="orderUnitPrice">
              <Translate contentKey="seswebApp.contracts.help.orderUnitPrice" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.orderUnitPrice}</dd>
          <dt>
            <span id="maximumOrderSettlementCondition">
              <Translate contentKey="seswebApp.contracts.maximumOrderSettlementCondition">Maximum Order Settlement Condition</Translate>
            </span>
            <UncontrolledTooltip target="maximumOrderSettlementCondition">
              <Translate contentKey="seswebApp.contracts.help.maximumOrderSettlementCondition" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.maximumOrderSettlementCondition}</dd>
          <dt>
            <span id="minimumOrderSettlementCondition">
              <Translate contentKey="seswebApp.contracts.minimumOrderSettlementCondition">Minimum Order Settlement Condition</Translate>
            </span>
            <UncontrolledTooltip target="minimumOrderSettlementCondition">
              <Translate contentKey="seswebApp.contracts.help.minimumOrderSettlementCondition" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.minimumOrderSettlementCondition}</dd>
          <dt>
            <span id="orderDeductionUnitPrice">
              <Translate contentKey="seswebApp.contracts.orderDeductionUnitPrice">Order Deduction Unit Price</Translate>
            </span>
            <UncontrolledTooltip target="orderDeductionUnitPrice">
              <Translate contentKey="seswebApp.contracts.help.orderDeductionUnitPrice" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.orderDeductionUnitPrice}</dd>
          <dt>
            <span id="orderSurchargeUnitPrice">
              <Translate contentKey="seswebApp.contracts.orderSurchargeUnitPrice">Order Surcharge Unit Price</Translate>
            </span>
            <UncontrolledTooltip target="orderSurchargeUnitPrice">
              <Translate contentKey="seswebApp.contracts.help.orderSurchargeUnitPrice" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.orderSurchargeUnitPrice}</dd>
          <dt>
            <span id="purchaseUnitPrice">
              <Translate contentKey="seswebApp.contracts.purchaseUnitPrice">Purchase Unit Price</Translate>
            </span>
            <UncontrolledTooltip target="purchaseUnitPrice">
              <Translate contentKey="seswebApp.contracts.help.purchaseUnitPrice" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.purchaseUnitPrice}</dd>
          <dt>
            <span id="maximumPurchaseSettlementCondition">
              <Translate contentKey="seswebApp.contracts.maximumPurchaseSettlementCondition">
                Maximum Purchase Settlement Condition
              </Translate>
            </span>
            <UncontrolledTooltip target="maximumPurchaseSettlementCondition">
              <Translate contentKey="seswebApp.contracts.help.maximumPurchaseSettlementCondition" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.maximumPurchaseSettlementCondition}</dd>
          <dt>
            <span id="minimumPurchaseSettlementCondition">
              <Translate contentKey="seswebApp.contracts.minimumPurchaseSettlementCondition">
                Minimum Purchase Settlement Condition
              </Translate>
            </span>
            <UncontrolledTooltip target="minimumPurchaseSettlementCondition">
              <Translate contentKey="seswebApp.contracts.help.minimumPurchaseSettlementCondition" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.minimumPurchaseSettlementCondition}</dd>
          <dt>
            <span id="purchaseDeductionUnitPrice">
              <Translate contentKey="seswebApp.contracts.purchaseDeductionUnitPrice">Purchase Deduction Unit Price</Translate>
            </span>
            <UncontrolledTooltip target="purchaseDeductionUnitPrice">
              <Translate contentKey="seswebApp.contracts.help.purchaseDeductionUnitPrice" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.purchaseDeductionUnitPrice}</dd>
          <dt>
            <span id="purchaseSurchargeUnitPrice">
              <Translate contentKey="seswebApp.contracts.purchaseSurchargeUnitPrice">Purchase Surcharge Unit Price</Translate>
            </span>
            <UncontrolledTooltip target="purchaseSurchargeUnitPrice">
              <Translate contentKey="seswebApp.contracts.help.purchaseSurchargeUnitPrice" />
            </UncontrolledTooltip>
          </dt>
          <dd>{contractsEntity.purchaseSurchargeUnitPrice}</dd>
          <dt>
            <Translate contentKey="seswebApp.contracts.project">Project</Translate>
          </dt>
          <dd>{contractsEntity.project ? contractsEntity.project.id : ''}</dd>
          <dt>
            <Translate contentKey="seswebApp.contracts.partnerManager">Partner Manager</Translate>
          </dt>
          <dd>{contractsEntity.partnerManager ? contractsEntity.partnerManager.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/contracts" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contracts/${contractsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContractsDetail;
