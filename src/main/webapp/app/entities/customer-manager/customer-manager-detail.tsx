import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './customer-manager.reducer';

export const CustomerManagerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const customerManagerEntity = useAppSelector(state => state.customerManager.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerManagerDetailsHeading">
          <Translate contentKey="seswebApp.customerManager.detail.title">CustomerManager</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customerManagerEntity.id}</dd>
          <dt>
            <span id="customerName">
              <Translate contentKey="seswebApp.customerManager.customerName">Customer Name</Translate>
            </span>
            <UncontrolledTooltip target="customerName">
              <Translate contentKey="seswebApp.customerManager.help.customerName" />
            </UncontrolledTooltip>
          </dt>
          <dd>{customerManagerEntity.customerName}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="seswebApp.customerManager.name">Name</Translate>
            </span>
            <UncontrolledTooltip target="name">
              <Translate contentKey="seswebApp.customerManager.help.name" />
            </UncontrolledTooltip>
          </dt>
          <dd>{customerManagerEntity.name}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="seswebApp.customerManager.email">Email</Translate>
            </span>
          </dt>
          <dd>{customerManagerEntity.email}</dd>
          <dt>
            <span id="phoneName">
              <Translate contentKey="seswebApp.customerManager.phoneName">Phone Name</Translate>
            </span>
          </dt>
          <dd>{customerManagerEntity.phoneName}</dd>
        </dl>
        <Button tag={Link} to="/customer-manager" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer-manager/${customerManagerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerManagerDetail;
