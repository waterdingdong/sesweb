import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sales-amount.reducer';

export const SalesAmountDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const salesAmountEntity = useAppSelector(state => state.salesAmount.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="salesAmountDetailsHeading">
          <Translate contentKey="seswebApp.salesAmount.detail.title">SalesAmount</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{salesAmountEntity.id}</dd>
          <dt>
            <span id="salesYm">
              <Translate contentKey="seswebApp.salesAmount.salesYm">Sales Ym</Translate>
            </span>
          </dt>
          <dd>{salesAmountEntity.salesYm}</dd>
          <dt>
            <span id="workTime">
              <Translate contentKey="seswebApp.salesAmount.workTime">Work Time</Translate>
            </span>
          </dt>
          <dd>{salesAmountEntity.workTime}</dd>
          <dt>
            <span id="billingAmount">
              <Translate contentKey="seswebApp.salesAmount.billingAmount">Billing Amount</Translate>
            </span>
          </dt>
          <dd>{salesAmountEntity.billingAmount}</dd>
          <dt>
            <Translate contentKey="seswebApp.salesAmount.contract">Contract</Translate>
          </dt>
          <dd>{salesAmountEntity.contract ? salesAmountEntity.contract.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/sales-amount" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sales-amount/${salesAmountEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SalesAmountDetail;
