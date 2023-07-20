import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContractPriceUpdateHistory } from 'app/shared/model/contract-price-update-history.model';
import { getEntities } from './contract-price-update-history.reducer';

export const ContractPriceUpdateHistory = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const contractPriceUpdateHistoryList = useAppSelector(state => state.contractPriceUpdateHistory.entities);
  const loading = useAppSelector(state => state.contractPriceUpdateHistory.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="contract-price-update-history-heading" data-cy="ContractPriceUpdateHistoryHeading">
        <Translate contentKey="seswebApp.contractPriceUpdateHistory.home.title">Contract Price Update Histories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="seswebApp.contractPriceUpdateHistory.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/contract-price-update-history/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="seswebApp.contractPriceUpdateHistory.home.createLabel">
              Create new Contract Price Update History
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {contractPriceUpdateHistoryList && contractPriceUpdateHistoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="seswebApp.contractPriceUpdateHistory.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contractPriceUpdateHistory.updateDate">Update Date</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contractPriceUpdateHistory.beforePrice">Before Price</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contractPriceUpdateHistory.afterPrice">After Price</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.contractPriceUpdateHistory.contract">Contract</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {contractPriceUpdateHistoryList.map((contractPriceUpdateHistory, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/contract-price-update-history/${contractPriceUpdateHistory.id}`} color="link" size="sm">
                      {contractPriceUpdateHistory.id}
                    </Button>
                  </td>
                  <td>
                    {contractPriceUpdateHistory.updateDate ? (
                      <TextFormat type="date" value={contractPriceUpdateHistory.updateDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{contractPriceUpdateHistory.beforePrice}</td>
                  <td>{contractPriceUpdateHistory.afterPrice}</td>
                  <td>
                    {contractPriceUpdateHistory.contract ? (
                      <Link to={`/contracts/${contractPriceUpdateHistory.contract.id}`}>{contractPriceUpdateHistory.contract.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/contract-price-update-history/${contractPriceUpdateHistory.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/contract-price-update-history/${contractPriceUpdateHistory.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/contract-price-update-history/${contractPriceUpdateHistory.id}/delete`}
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
              <Translate contentKey="seswebApp.contractPriceUpdateHistory.home.notFound">
                No Contract Price Update Histories found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ContractPriceUpdateHistory;
