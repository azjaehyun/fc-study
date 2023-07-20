import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IApplication } from 'app/shared/model/application.model';
import { getEntities } from './application.reducer';

export const Application = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const applicationList = useAppSelector(state => state.application.entities);
  const loading = useAppSelector(state => state.application.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="application-heading" data-cy="ApplicationHeading">
        <Translate contentKey="myApp.application.home.title">Applications</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myApp.application.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/application/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myApp.application.home.createLabel">Create new Application</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {applicationList && applicationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myApp.application.applicationId">Application Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.application.applicationDate">Application Date</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.application.resume">Resume</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.application.jobPosting">Job Posting</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.application.applicant">Applicant</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {applicationList.map((application, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/application/${application.applicationId}`} color="link" size="sm">
                      {application.applicationId}
                    </Button>
                  </td>
                  <td>
                    {application.applicationDate ? (
                      <TextFormat type="date" value={application.applicationDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {application.resume ? <Link to={`/resume/${application.resume.resumeId}`}>{application.resume.resumeId}</Link> : ''}
                  </td>
                  <td>
                    {application.jobPosting ? (
                      <Link to={`/job-posting/${application.jobPosting.jobId}`}>{application.jobPosting.jobId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {application.applicant ? (
                      <Link to={`/applicant/${application.applicant.applicantId}`}>{application.applicant.applicantId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/application/${application.applicationId}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/application/${application.applicationId}/edit`}
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
                        to={`/application/${application.applicationId}/delete`}
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
              <Translate contentKey="myApp.application.home.notFound">No Applications found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Application;
