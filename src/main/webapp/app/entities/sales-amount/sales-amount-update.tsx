import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContracts } from 'app/shared/model/contracts.model';
import { getEntities as getContracts } from 'app/entities/contracts/contracts.reducer';
import { ISalesAmount } from 'app/shared/model/sales-amount.model';
import { getEntity, updateEntity, createEntity, reset } from './sales-amount.reducer';

export const SalesAmountUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contracts = useAppSelector(state => state.contracts.entities);
  const salesAmountEntity = useAppSelector(state => state.salesAmount.entity);
  const loading = useAppSelector(state => state.salesAmount.loading);
  const updating = useAppSelector(state => state.salesAmount.updating);
  const updateSuccess = useAppSelector(state => state.salesAmount.updateSuccess);

  const handleClose = () => {
    navigate('/sales-amount');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getContracts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...salesAmountEntity,
      ...values,
      contract: contracts.find(it => it.id.toString() === values.contract.toString()),
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
          ...salesAmountEntity,
          contract: salesAmountEntity?.contract?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="seswebApp.salesAmount.home.createOrEditLabel" data-cy="SalesAmountCreateUpdateHeading">
            <Translate contentKey="seswebApp.salesAmount.home.createOrEditLabel">Create or edit a SalesAmount</Translate>
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
                  id="sales-amount-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('seswebApp.salesAmount.salesYm')}
                id="sales-amount-salesYm"
                name="salesYm"
                data-cy="salesYm"
                type="text"
              />
              <ValidatedField
                label={translate('seswebApp.salesAmount.workTime')}
                id="sales-amount-workTime"
                name="workTime"
                data-cy="workTime"
                type="text"
              />
              <ValidatedField
                label={translate('seswebApp.salesAmount.billingAmount')}
                id="sales-amount-billingAmount"
                name="billingAmount"
                data-cy="billingAmount"
                type="text"
              />
              <ValidatedField
                id="sales-amount-contract"
                name="contract"
                data-cy="contract"
                label={translate('seswebApp.salesAmount.contract')}
                type="select"
              >
                <option value="" key="0" />
                {contracts
                  ? contracts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sales-amount" replace color="info">
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

export default SalesAmountUpdate;
