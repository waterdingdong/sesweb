import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICustomerManager } from 'app/shared/model/customer-manager.model';
import { getEntity, updateEntity, createEntity, reset } from './customer-manager.reducer';

export const CustomerManagerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customerManagerEntity = useAppSelector(state => state.customerManager.entity);
  const loading = useAppSelector(state => state.customerManager.loading);
  const updating = useAppSelector(state => state.customerManager.updating);
  const updateSuccess = useAppSelector(state => state.customerManager.updateSuccess);

  const handleClose = () => {
    navigate('/customer-manager');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...customerManagerEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...customerManagerEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="seswebApp.customerManager.home.createOrEditLabel" data-cy="CustomerManagerCreateUpdateHeading">
            <Translate contentKey="seswebApp.customerManager.home.createOrEditLabel">Create or edit a CustomerManager</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="customer-manager-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('seswebApp.customerManager.customerName')}
                id="customer-manager-customerName"
                name="customerName"
                data-cy="customerName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="customerNameLabel">
                <Translate contentKey="seswebApp.customerManager.help.customerName" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.customerManager.name')}
                id="customer-manager-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="nameLabel">
                <Translate contentKey="seswebApp.customerManager.help.name" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.customerManager.email')}
                id="customer-manager-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('seswebApp.customerManager.phoneName')}
                id="customer-manager-phoneName"
                name="phoneName"
                data-cy="phoneName"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/customer-manager" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CustomerManagerUpdate;
