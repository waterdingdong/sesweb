import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISalesAmount } from 'app/shared/model/sales-amount.model';
import { getEntities } from './sales-amount.reducer';

export const SalesAmount = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const salesAmountList = useAppSelector(state => state.salesAmount.entities);
  const loading = useAppSelector(state => state.salesAmount.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="sales-amount-heading" data-cy="SalesAmountHeading">
        <Translate contentKey="seswebApp.salesAmount.home.title">Sales Amounts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="seswebApp.salesAmount.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/sales-amount/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="seswebApp.salesAmount.home.createLabel">Create new Sales Amount</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {salesAmountList && salesAmountList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="seswebApp.salesAmount.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.salesAmount.salesYm">Sales Ym</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.salesAmount.workTime">Work Time</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.salesAmount.billingAmount">Billing Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.salesAmount.contract">Contract</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {salesAmountList.map((salesAmount, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sales-amount/${salesAmount.id}`} color="link" size="sm">
                      {salesAmount.id}
                    </Button>
                  </td>
                  <td>{salesAmount.salesYm}</td>
                  <td>{salesAmount.workTime}</td>
                  <td>{salesAmount.billingAmount}</td>
                  <td>{salesAmount.contract ? <Link to={`/contracts/${salesAmount.contract.id}`}>{salesAmount.contract.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/sales-amount/${salesAmount.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/sales-amount/${salesAmount.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sales-amount/${salesAmount.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="seswebApp.salesAmount.home.notFound">No Sales Amounts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SalesAmount;
