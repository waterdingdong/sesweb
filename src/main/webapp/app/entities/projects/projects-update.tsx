import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICustomerManager } from 'app/shared/model/customer-manager.model';
import { getEntities as getCustomerManagers } from 'app/entities/customer-manager/customer-manager.reducer';
import { IProjects } from 'app/shared/model/projects.model';
import { getEntity, updateEntity, createEntity, reset } from './projects.reducer';

export const ProjectsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customerManagers = useAppSelector(state => state.customerManager.entities);
  const projectsEntity = useAppSelector(state => state.projects.entity);
  const loading = useAppSelector(state => state.projects.loading);
  const updating = useAppSelector(state => state.projects.updating);
  const updateSuccess = useAppSelector(state => state.projects.updateSuccess);

  const handleClose = () => {
    navigate('/projects');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCustomerManagers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...projectsEntity,
      ...values,
      customerManager: customerManagers.find(it => it.id.toString() === values.customerManager.toString()),
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
          ...projectsEntity,
          customerManager: projectsEntity?.customerManager?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="seswebApp.projects.home.createOrEditLabel" data-cy="ProjectsCreateUpdateHeading">
            <Translate contentKey="seswebApp.projects.home.createOrEditLabel">Create or edit a Projects</Translate>
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
                  id="projects-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('seswebApp.projects.name')} id="projects-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                id="projects-customerManager"
                name="customerManager"
                data-cy="customerManager"
                label={translate('seswebApp.projects.customerManager')}
                type="select"
              >
                <option value="" key="0" />
                {customerManagers
                  ? customerManagers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/projects" replace color="info">
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

export default ProjectsUpdate;
