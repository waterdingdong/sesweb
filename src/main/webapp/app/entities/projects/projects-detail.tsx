import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './projects.reducer';

export const ProjectsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const projectsEntity = useAppSelector(state => state.projects.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectsDetailsHeading">
          <Translate contentKey="seswebApp.projects.detail.title">Projects</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{projectsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="seswebApp.projects.name">Name</Translate>
            </span>
          </dt>
          <dd>{projectsEntity.name}</dd>
          <dt>
            <Translate contentKey="seswebApp.projects.customerManager">Customer Manager</Translate>
          </dt>
          <dd>{projectsEntity.customerManager ? projectsEntity.customerManager.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/projects" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/projects/${projectsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectsDetail;
