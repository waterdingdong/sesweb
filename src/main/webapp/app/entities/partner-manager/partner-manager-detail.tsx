import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './partner-manager.reducer';

export const PartnerManagerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const partnerManagerEntity = useAppSelector(state => state.partnerManager.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="partnerManagerDetailsHeading">
          <Translate contentKey="seswebApp.partnerManager.detail.title">PartnerManager</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{partnerManagerEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="seswebApp.partnerManager.name">Name</Translate>
            </span>
          </dt>
          <dd>{partnerManagerEntity.name}</dd>
          <dt>
            <span id="companyName">
              <Translate contentKey="seswebApp.partnerManager.companyName">Company Name</Translate>
            </span>
          </dt>
          <dd>{partnerManagerEntity.companyName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="seswebApp.partnerManager.email">Email</Translate>
            </span>
          </dt>
          <dd>{partnerManagerEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="seswebApp.partnerManager.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{partnerManagerEntity.phoneNumber}</dd>
          <dt>
            <span id="wechatId">
              <Translate contentKey="seswebApp.partnerManager.wechatId">Wechat Id</Translate>
            </span>
          </dt>
          <dd>{partnerManagerEntity.wechatId}</dd>
        </dl>
        <Button tag={Link} to="/partner-manager" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/partner-manager/${partnerManagerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PartnerManagerDetail;
