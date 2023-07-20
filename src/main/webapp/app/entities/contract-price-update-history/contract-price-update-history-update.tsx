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
import { IContractPriceUpdateHistory } from 'app/shared/model/contract-price-update-history.model';
import { getEntity, updateEntity, createEntity, reset } from './contract-price-update-history.reducer';

export const ContractPriceUpdateHistoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const contracts = useAppSelector(state => state.contracts.entities);
  const contractPriceUpdateHistoryEntity = useAppSelector(state => state.contractPriceUpdateHistory.entity);
  const loading = useAppSelector(state => state.contractPriceUpdateHistory.loading);
  const updating = useAppSelector(state => state.contractPriceUpdateHistory.updating);
  const updateSuccess = useAppSelector(state => state.contractPriceUpdateHistory.updateSuccess);

  const handleClose = () => {
    navigate('/contract-price-update-history');
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
    values.updateDate = convertDateTimeToServer(values.updateDate);

    const entity = {
      ...contractPriceUpdateHistoryEntity,
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
      ? {
          updateDate: displayDefaultDateTime(),
        }
      : {
          ...contractPriceUpdateHistoryEntity,
          updateDate: convertDateTimeFromServer(contractPriceUpdateHistoryEntity.updateDate),
          contract: contractPriceUpdateHistoryEntity?.contract?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="seswebApp.contractPriceUpdateHistory.home.createOrEditLabel" data-cy="ContractPriceUpdateHistoryCreateUpdateHeading">
            <Translate contentKey="seswebApp.contractPriceUpdateHistory.home.createOrEditLabel">
              Create or edit a ContractPriceUpdateHistory
            </Translate>
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
                  id="contract-price-update-history-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('seswebApp.contractPriceUpdateHistory.updateDate')}
                id="contract-price-update-history-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('seswebApp.contractPriceUpdateHistory.beforePrice')}
                id="contract-price-update-history-beforePrice"
                name="beforePrice"
                data-cy="beforePrice"
                type="text"
              />
              <ValidatedField
                label={translate('seswebApp.contractPriceUpdateHistory.afterPrice')}
                id="contract-price-update-history-afterPrice"
                name="afterPrice"
                data-cy="afterPrice"
                type="text"
              />
              <ValidatedField
                id="contract-price-update-history-contract"
                name="contract"
                data-cy="contract"
                label={translate('seswebApp.contractPriceUpdateHistory.contract')}
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
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/contract-price-update-history"
                replace
                color="info"
              >
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

export default ContractPriceUpdateHistoryUpdate;
