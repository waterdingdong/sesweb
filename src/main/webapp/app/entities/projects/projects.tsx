import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProjects } from 'app/shared/model/projects.model';
import { getEntities } from './projects.reducer';

export const Projects = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const projectsList = useAppSelector(state => state.projects.entities);
  const loading = useAppSelector(state => state.projects.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="projects-heading" data-cy="ProjectsHeading">
        <Translate contentKey="seswebApp.projects.home.title">Projects</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="seswebApp.projects.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/projects/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="seswebApp.projects.home.createLabel">Create new Projects</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {projectsList && projectsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="seswebApp.projects.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.projects.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="seswebApp.projects.customerManager">Customer Manager</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {projectsList.map((projects, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/projects/${projects.id}`} color="link" size="sm">
                      {projects.id}
                    </Button>
                  </td>
                  <td>{projects.name}</td>
                  <td>
                    {projects.customerManager ? (
                      <Link to={`/customer-manager/${projects.customerManager.id}`}>{projects.customerManager.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/projects/${projects.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/projects/${projects.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/projects/${projects.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="seswebApp.projects.home.notFound">No Projects found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Projects;
