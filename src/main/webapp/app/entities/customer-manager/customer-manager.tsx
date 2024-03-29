import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICustomerManager } from 'app/shared/model/customer-manager.model';
import { getEntities } from './customer-manager.reducer';

export const CustomerManager = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const customerManagerList = useAppSelector(state => state.customerManager.entities);
  const loading = useAppSelector(state => state.customerManager.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="customer-manager-heading" data-cy="CustomerManagerHeading">
        <Translate contentKey="seswebApp.customerManager.home.title">Customer Managers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="seswebApp.customerManager.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/customer-manager/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="seswebApp.customerManager.home.createLabel">Create new Customer Manager</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {customerManagerList && customerManagerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="seswebApp.customerManager.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.customerManager.customerName">Customer Name</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.customerManager.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.customerManager.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.customerManager.phoneName">Phone Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {customerManagerList.map((customerManager, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/customer-manager/${customerManager.id}`} color="link" size="sm">
                      {customerManager.id}
                    </Button>
                  </td>
                  <td>{customerManager.customerName}</td>
                  <td>{customerManager.name}</td>
                  <td>{customerManager.email}</td>
                  <td>{customerManager.phoneName}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/customer-manager/${customerManager.id}`}
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
                        to={`/customer-manager/${customerManager.id}/edit`}
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
                        to={`/customer-manager/${customerManager.id}/delete`}
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
              <Translate contentKey="seswebApp.customerManager.home.notFound">No Customer Managers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default CustomerManager;
