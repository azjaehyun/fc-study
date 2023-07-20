import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IApplicant } from 'app/shared/model/applicant.model';
import { getEntities } from './applicant.reducer';

export const Applicant = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const applicantList = useAppSelector(state => state.applicant.entities);
  const loading = useAppSelector(state => state.applicant.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="applicant-heading" data-cy="ApplicantHeading">
        <Translate contentKey="myApp.applicant.home.title">Applicants</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myApp.applicant.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/applicant/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myApp.applicant.home.createLabel">Create new Applicant</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {applicantList && applicantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myApp.applicant.applicantId">Applicant Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.applicant.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.applicant.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.applicant.phone">Phone</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {applicantList.map((applicant, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/applicant/${applicant.applicantId}`} color="link" size="sm">
                      {applicant.applicantId}
                    </Button>
                  </td>
                  <td>{applicant.name}</td>
                  <td>{applicant.email}</td>
                  <td>{applicant.phone}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/applicant/${applicant.applicantId}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/applicant/${applicant.applicantId}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/applicant/${applicant.applicantId}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="myApp.applicant.home.notFound">No Applicants found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Applicant;
