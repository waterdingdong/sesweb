import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import {
  Form,
  FormGroup,
  CardHeader,
  Card,
  CardBody,
  CardTitle,
  CardText,
  Container,
  Row,
  Col,
  Input,
  Label,
  Button,
  Table,
} from 'reactstrap';
import { Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPartnerManager } from 'app/shared/model/partner-manager.model';
import { getEntities, selectEntities } from './partner-manager.reducer';

export const PartnerManager = () => {
  const [searchField, setSearchField] = useState(useAppSelector(state => state.partnerManager.searchField));
  const [searchValue, setSearchValue] = useState(useAppSelector(state => state.partnerManager.searchValue));
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const partnerManagerList = useAppSelector(state => state.partnerManager.entities);
  const loading = useAppSelector(state => state.partnerManager.loading);

  useEffect(() => {
    //dispatch(getEntities({}));
    dispatch(selectEntities({ searchField, searchValue }));
  }, []);

  const handleSyncList = () => {
    setSearchField('name');
    setSearchValue('');
    dispatch(getEntities({}));
  };

  const handleValidSubmit = e => {
    e.preventDefault();
    dispatch(selectEntities({ searchField, searchValue }));
  };

  return (
    <div>
      <h2 id="partner-manager-heading" data-cy="PartnerManagerHeading">
        <Translate contentKey="seswebApp.partnerManager.home.title">Partner Managers</Translate>
      </h2>
      <div className="mb-1">
        <Form id="search-form" onSubmit={handleValidSubmit}>
          <Row>
            <Col xs="12" md="4" className="mb-2">
              <Input
                type="select"
                name="searchField"
                value={searchField}
                onChange={e => {
                  setSearchField(e.target.value);
                }}
                id="searchField"
              >
                <option value="name">担当者名</option>
                <option value="companyName">会社名</option>
              </Input>
            </Col>
            <Col xs="12" md="4" className="mb-2">
              <Input
                type="search"
                name="searchValue"
                value={searchValue}
                onChange={e => {
                  setSearchValue(e.target.value);
                }}
                defaultValue={searchValue}
                id="searchValue"
                placeholder=""
              />
            </Col>
            <Col xs="5" md="2" className="mb-2">
              <Button color="info" type="submit">
                検索
              </Button>
              <Button className="mx-2" color="info" onClick={handleSyncList} disabled={loading}>
                <FontAwesomeIcon icon="sync" spin={loading} />
              </Button>
            </Col>
            <Col xs="7" md="2" className="mb-2">
              <Link
                to="/partner-manager/new"
                className="btn btn-primary jh-create-entity"
                id="jh-create-entity"
                data-cy="entityCreateButton"
              >
                <FontAwesomeIcon icon="plus" />
                &nbsp;
                <Translate contentKey="seswebApp.partnerManager.home.createLabel">Create new Partner Manager</Translate>
              </Link>
            </Col>
          </Row>
        </Form>
      </div>
      {/*
        <div className="d-flex justify-content-end">

          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="seswebApp.partnerManager.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/partner-manager/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="seswebApp.partnerManager.home.createLabel">Create new Partner Manager</Translate>
          </Link>
        </div>
      */}
      <div className="mt-3">
        {partnerManagerList && partnerManagerList.length > 0 ? (
          <Row>
            {partnerManagerList.map((partnerManager, i) => (
              <Col sm="3" className="mb-3">
                <Card body>
                  <CardHeader>担当者名：{partnerManager.name}</CardHeader>
                  <CardText>
                    <ul>
                      <li>会社名：{partnerManager.companyName}</li>
                      <li>メール：{partnerManager.email}</li>
                      <li>電話番号：{partnerManager.phoneNumber}</li>
                      <li>wechat：{partnerManager.wechatId}</li>
                    </ul>
                  </CardText>
                  <Button tag={Link} to={`/partner-manager/${partnerManager.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                    <FontAwesomeIcon icon="eye" />{' '}
                    <span className="d-none d-md-inline">
                      <Translate contentKey="entity.action.view">View</Translate>
                    </span>
                  </Button>
                  <Button tag={Link} to={`/partner-manager/${partnerManager.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                    <FontAwesomeIcon icon="pencil-alt" />{' '}
                    <span className="d-none d-md-inline">
                      <Translate contentKey="entity.action.edit">Edit</Translate>
                    </span>
                  </Button>
                  <Button
                    tag={Link}
                    to={`/partner-manager/${partnerManager.id}/delete`}
                    color="danger"
                    size="sm"
                    data-cy="entityDeleteButton"
                  >
                    <FontAwesomeIcon icon="trash" />{' '}
                    <span className="d-none d-md-inline">
                      <Translate contentKey="entity.action.delete">Delete</Translate>
                    </span>
                  </Button>
                </Card>
              </Col>
            ))}
          </Row>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="seswebApp.partnerManager.home.notFound">No Partner Managers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PartnerManager;
