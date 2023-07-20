import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contract-price-update-history.reducer';

export const ContractPriceUpdateHistoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const contractPriceUpdateHistoryEntity = useAppSelector(state => state.contractPriceUpdateHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contractPriceUpdateHistoryDetailsHeading">
          <Translate contentKey="seswebApp.contractPriceUpdateHistory.detail.title">ContractPriceUpdateHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{contractPriceUpdateHistoryEntity.id}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="seswebApp.contractPriceUpdateHistory.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {contractPriceUpdateHistoryEntity.updateDate ? (
              <TextFormat value={contractPriceUpdateHistoryEntity.updateDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="beforePrice">
              <Translate contentKey="seswebApp.contractPriceUpdateHistory.beforePrice">Before Price</Translate>
            </span>
          </dt>
          <dd>{contractPriceUpdateHistoryEntity.beforePrice}</dd>
          <dt>
            <span id="afterPrice">
              <Translate contentKey="seswebApp.contractPriceUpdateHistory.afterPrice">After Price</Translate>
            </span>
          </dt>
          <dd>{contractPriceUpdateHistoryEntity.afterPrice}</dd>
          <dt>
            <Translate contentKey="seswebApp.contractPriceUpdateHistory.contract">Contract</Translate>
          </dt>
          <dd>{contractPriceUpdateHistoryEntity.contract ? contractPriceUpdateHistoryEntity.contract.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/contract-price-update-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contract-price-update-history/${contractPriceUpdateHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContractPriceUpdateHistoryDetail;
