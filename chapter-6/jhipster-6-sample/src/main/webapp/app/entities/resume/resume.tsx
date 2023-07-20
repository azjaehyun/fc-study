import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IResume } from 'app/shared/model/resume.model';
import { getEntities } from './resume.reducer';

export const Resume = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const resumeList = useAppSelector(state => state.resume.entities);
  const loading = useAppSelector(state => state.resume.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="resume-heading" data-cy="ResumeHeading">
        <Translate contentKey="myApp.resume.home.title">Resumes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myApp.resume.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/resume/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myApp.resume.home.createLabel">Create new Resume</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {resumeList && resumeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myApp.resume.resumeId">Resume Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.resume.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.resume.content">Content</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.resume.submittedDate">Submitted Date</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.resume.applicant">Applicant</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {resumeList.map((resume, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/resume/${resume.resumeId}`} color="link" size="sm">
                      {resume.resumeId}
                    </Button>
                  </td>
                  <td>{resume.title}</td>
                  <td>{resume.content}</td>
                  <td>{resume.submittedDate ? <TextFormat type="date" value={resume.submittedDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {resume.applicant ? <Link to={`/applicant/${resume.applicant.applicantId}`}>{resume.applicant.applicantId}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/resume/${resume.resumeId}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/resume/${resume.resumeId}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/resume/${resume.resumeId}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="myApp.resume.home.notFound">No Resumes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Resume;
