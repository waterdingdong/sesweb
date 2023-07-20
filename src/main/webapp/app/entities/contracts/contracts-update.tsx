import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText, UncontrolledTooltip } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProjects } from 'app/shared/model/projects.model';
import { getEntities as getProjects } from 'app/entities/projects/projects.reducer';
import { IPartnerManager } from 'app/shared/model/partner-manager.model';
import { getEntities as getPartnerManagers } from 'app/entities/partner-manager/partner-manager.reducer';
import { IContracts } from 'app/shared/model/contracts.model';
import { getEntity, updateEntity, createEntity, reset } from './contracts.reducer';

export const ContractsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const projects = useAppSelector(state => state.projects.entities);
  const partnerManagers = useAppSelector(state => state.partnerManager.entities);
  const contractsEntity = useAppSelector(state => state.contracts.entity);
  const loading = useAppSelector(state => state.contracts.loading);
  const updating = useAppSelector(state => state.contracts.updating);
  const updateSuccess = useAppSelector(state => state.contracts.updateSuccess);

  const handleClose = () => {
    navigate('/contracts');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProjects({}));
    dispatch(getPartnerManagers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.contractStartDate = convertDateTimeToServer(values.contractStartDate);
    values.contractEndDate = convertDateTimeToServer(values.contractEndDate);

    const entity = {
      ...contractsEntity,
      ...values,
      project: projects.find(it => it.id.toString() === values.project.toString()),
      partnerManager: partnerManagers.find(it => it.id.toString() === values.partnerManager.toString()),
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
          contractStartDate: displayDefaultDateTime(),
          contractEndDate: displayDefaultDateTime(),
        }
      : {
          ...contractsEntity,
          contractStartDate: convertDateTimeFromServer(contractsEntity.contractStartDate),
          contractEndDate: convertDateTimeFromServer(contractsEntity.contractEndDate),
          project: contractsEntity?.project?.id,
          partnerManager: contractsEntity?.partnerManager?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="seswebApp.contracts.home.createOrEditLabel" data-cy="ContractsCreateUpdateHeading">
            <Translate contentKey="seswebApp.contracts.home.createOrEditLabel">Create or edit a Contracts</Translate>
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
                  id="contracts-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('seswebApp.contracts.lastName')}
                id="contracts-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="lastNameLabel">
                <Translate contentKey="seswebApp.contracts.help.lastName" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.firstName')}
                id="contracts-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <UncontrolledTooltip target="firstNameLabel">
                <Translate contentKey="seswebApp.contracts.help.firstName" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.ecnId')}
                id="contracts-ecnId"
                name="ecnId"
                data-cy="ecnId"
                type="text"
              />
              <UncontrolledTooltip target="ecnIdLabel">
                <Translate contentKey="seswebApp.contracts.help.ecnId" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.manager')}
                id="contracts-manager"
                name="manager"
                data-cy="manager"
                type="text"
              />
              <UncontrolledTooltip target="managerLabel">
                <Translate contentKey="seswebApp.contracts.help.manager" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.managerEcnId')}
                id="contracts-managerEcnId"
                name="managerEcnId"
                data-cy="managerEcnId"
                type="text"
              />
              <UncontrolledTooltip target="managerEcnIdLabel">
                <Translate contentKey="seswebApp.contracts.help.managerEcnId" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.contractStartDate')}
                id="contracts-contractStartDate"
                name="contractStartDate"
                data-cy="contractStartDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <UncontrolledTooltip target="contractStartDateLabel">
                <Translate contentKey="seswebApp.contracts.help.contractStartDate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.contractEndDate')}
                id="contracts-contractEndDate"
                name="contractEndDate"
                data-cy="contractEndDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <UncontrolledTooltip target="contractEndDateLabel">
                <Translate contentKey="seswebApp.contracts.help.contractEndDate" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.orderUnitPrice')}
                id="contracts-orderUnitPrice"
                name="orderUnitPrice"
                data-cy="orderUnitPrice"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <UncontrolledTooltip target="orderUnitPriceLabel">
                <Translate contentKey="seswebApp.contracts.help.orderUnitPrice" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.maximumOrderSettlementCondition')}
                id="contracts-maximumOrderSettlementCondition"
                name="maximumOrderSettlementCondition"
                data-cy="maximumOrderSettlementCondition"
                type="text"
              />
              <UncontrolledTooltip target="maximumOrderSettlementConditionLabel">
                <Translate contentKey="seswebApp.contracts.help.maximumOrderSettlementCondition" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.minimumOrderSettlementCondition')}
                id="contracts-minimumOrderSettlementCondition"
                name="minimumOrderSettlementCondition"
                data-cy="minimumOrderSettlementCondition"
                type="text"
              />
              <UncontrolledTooltip target="minimumOrderSettlementConditionLabel">
                <Translate contentKey="seswebApp.contracts.help.minimumOrderSettlementCondition" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.orderDeductionUnitPrice')}
                id="contracts-orderDeductionUnitPrice"
                name="orderDeductionUnitPrice"
                data-cy="orderDeductionUnitPrice"
                type="text"
              />
              <UncontrolledTooltip target="orderDeductionUnitPriceLabel">
                <Translate contentKey="seswebApp.contracts.help.orderDeductionUnitPrice" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.orderSurchargeUnitPrice')}
                id="contracts-orderSurchargeUnitPrice"
                name="orderSurchargeUnitPrice"
                data-cy="orderSurchargeUnitPrice"
                type="text"
              />
              <UncontrolledTooltip target="orderSurchargeUnitPriceLabel">
                <Translate contentKey="seswebApp.contracts.help.orderSurchargeUnitPrice" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.purchaseUnitPrice')}
                id="contracts-purchaseUnitPrice"
                name="purchaseUnitPrice"
                data-cy="purchaseUnitPrice"
                type="text"
              />
              <UncontrolledTooltip target="purchaseUnitPriceLabel">
                <Translate contentKey="seswebApp.contracts.help.purchaseUnitPrice" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.maximumPurchaseSettlementCondition')}
                id="contracts-maximumPurchaseSettlementCondition"
                name="maximumPurchaseSettlementCondition"
                data-cy="maximumPurchaseSettlementCondition"
                type="text"
              />
              <UncontrolledTooltip target="maximumPurchaseSettlementConditionLabel">
                <Translate contentKey="seswebApp.contracts.help.maximumPurchaseSettlementCondition" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.minimumPurchaseSettlementCondition')}
                id="contracts-minimumPurchaseSettlementCondition"
                name="minimumPurchaseSettlementCondition"
                data-cy="minimumPurchaseSettlementCondition"
                type="text"
              />
              <UncontrolledTooltip target="minimumPurchaseSettlementConditionLabel">
                <Translate contentKey="seswebApp.contracts.help.minimumPurchaseSettlementCondition" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.purchaseDeductionUnitPrice')}
                id="contracts-purchaseDeductionUnitPrice"
                name="purchaseDeductionUnitPrice"
                data-cy="purchaseDeductionUnitPrice"
                type="text"
              />
              <UncontrolledTooltip target="purchaseDeductionUnitPriceLabel">
                <Translate contentKey="seswebApp.contracts.help.purchaseDeductionUnitPrice" />
              </UncontrolledTooltip>
              <ValidatedField
                label={translate('seswebApp.contracts.purchaseSurchargeUnitPrice')}
                id="contracts-purchaseSurchargeUnitPrice"
                name="purchaseSurchargeUnitPrice"
                data-cy="purchaseSurchargeUnitPrice"
                type="text"
              />
              <UncontrolledTooltip target="purchaseSurchargeUnitPriceLabel">
                <Translate contentKey="seswebApp.contracts.help.purchaseSurchargeUnitPrice" />
              </UncontrolledTooltip>
              <ValidatedField
                id="contracts-project"
                name="project"
                data-cy="project"
                label={translate('seswebApp.contracts.project')}
                type="select"
              >
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="contracts-partnerManager"
                name="partnerManager"
                data-cy="partnerManager"
                label={translate('seswebApp.contracts.partnerManager')}
                type="select"
              >
                <option value="" key="0" />
                {partnerManagers
                  ? partnerManagers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/contracts" replace color="info">
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

export default ContractsUpdate;
