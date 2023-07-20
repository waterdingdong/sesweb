import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContracts } from 'app/shared/model/contracts.model';
import { getEntities } from './contracts.reducer';

export const Contracts = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const contractsList = useAppSelector(state => state.contracts.entities);
  const loading = useAppSelector(state => state.contracts.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="contracts-heading" data-cy="ContractsHeading">
        <Translate contentKey="seswebApp.contracts.home.title">Contracts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="seswebApp.contracts.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/contracts/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="seswebApp.contracts.home.createLabel">Create new Contracts</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {contractsList && contractsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="seswebApp.contracts.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.lastName">Last Name</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.firstName">First Name</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.ecnId">Ecn Id</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.manager">Manager</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.managerEcnId">Manager Ecn Id</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.contractStartDate">Contract Start Date</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.contractEndDate">Contract End Date</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.orderUnitPrice">Order Unit Price</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.maximumOrderSettlementCondition">Maximum Order Settlement Condition</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.minimumOrderSettlementCondition">Minimum Order Settlement Condition</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.orderDeductionUnitPrice">Order Deduction Unit Price</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.orderSurchargeUnitPrice">Order Surcharge Unit Price</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.purchaseUnitPrice">Purchase Unit Price</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.maximumPurchaseSettlementCondition">
                    Maximum Purchase Settlement Condition
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.minimumPurchaseSettlementCondition">
                    Minimum Purchase Settlement Condition
                  </Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.purchaseDeductionUnitPrice">Purchase Deduction Unit Price</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.purchaseSurchargeUnitPrice">Purchase Surcharge Unit Price</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.project">Project</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contracts.partnerManager">Partner Manager</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {contractsList.map((contracts, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/contracts/${contracts.id}`} color="link" size="sm">
                      {contracts.id}
                    </Button>
                  </td>
                  <td>{contracts.lastName}</td>
                  <td>{contracts.firstName}</td>
                  <td>{contracts.ecnId}</td>
                  <td>{contracts.manager}</td>
                  <td>{contracts.managerEcnId}</td>
                  <td>
                    {contracts.contractStartDate ? (
                      <TextFormat type="date" value={contracts.contractStartDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {contracts.contractEndDate ? (
                      <TextFormat type="date" value={contracts.contractEndDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{contracts.orderUnitPrice}</td>
                  <td>{contracts.maximumOrderSettlementCondition}</td>
                  <td>{contracts.minimumOrderSettlementCondition}</td>
                  <td>{contracts.orderDeductionUnitPrice}</td>
                  <td>{contracts.orderSurchargeUnitPrice}</td>
                  <td>{contracts.purchaseUnitPrice}</td>
                  <td>{contracts.maximumPurchaseSettlementCondition}</td>
                  <td>{contracts.minimumPurchaseSettlementCondition}</td>
                  <td>{contracts.purchaseDeductionUnitPrice}</td>
                  <td>{contracts.purchaseSurchargeUnitPrice}</td>
                  <td>{contracts.project ? <Link to={`/projects/${contracts.project.id}`}>{contracts.project.id}</Link> : ''}</td>
                  <td>
                    {contracts.partnerManager ? (
                      <Link to={`/partner-manager/${contracts.partnerManager.id}`}>{contracts.partnerManager.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/contracts/${contracts.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/contracts/${contracts.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/contracts/${contracts.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="seswebApp.contracts.home.notFound">No Contracts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Contracts;
