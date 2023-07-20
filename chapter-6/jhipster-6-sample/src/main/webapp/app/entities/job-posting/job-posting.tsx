import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJobPosting } from 'app/shared/model/job-posting.model';
import { getEntities } from './job-posting.reducer';

export const JobPosting = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const jobPostingList = useAppSelector(state => state.jobPosting.entities);
  const loading = useAppSelector(state => state.jobPosting.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="job-posting-heading" data-cy="JobPostingHeading">
        <Translate contentKey="myApp.jobPosting.home.title">Job Postings</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myApp.jobPosting.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/job-posting/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myApp.jobPosting.home.createLabel">Create new Job Posting</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {jobPostingList && jobPostingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myApp.jobPosting.jobId">Job Id</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.jobPosting.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.jobPosting.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.jobPosting.location">Location</Translate>
                </th>
                <th>
                  <Translate contentKey="myApp.jobPosting.postedDate">Posted Date</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {jobPostingList.map((jobPosting, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/job-posting/${jobPosting.jobId}`} color="link" size="sm">
                      {jobPosting.jobId}
                    </Button>
                  </td>
                  <td>{jobPosting.title}</td>
                  <td>{jobPosting.description}</td>
                  <td>{jobPosting.location}</td>
                  <td>
                    {jobPosting.postedDate ? <TextFormat type="date" value={jobPosting.postedDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/job-posting/${jobPosting.jobId}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/job-posting/${jobPosting.jobId}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/job-posting/${jobPosting.jobId}/delete`}
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
              <Translate contentKey="myApp.jobPosting.home.notFound">No Job Postings found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default JobPosting;
