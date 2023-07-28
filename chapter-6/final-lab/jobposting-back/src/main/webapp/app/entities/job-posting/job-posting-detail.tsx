import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job-posting.reducer';

export const JobPostingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobPostingEntity = useAppSelector(state => state.jobPosting.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobPostingDetailsHeading">
          <Translate contentKey="jobpostingApp.jobPosting.detail.title">JobPosting</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="jobId">
              <Translate contentKey="jobpostingApp.jobPosting.jobId">Job Id</Translate>
            </span>
          </dt>
          <dd>{jobPostingEntity.jobId}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="jobpostingApp.jobPosting.title">Title</Translate>
            </span>
          </dt>
          <dd>{jobPostingEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jobpostingApp.jobPosting.description">Description</Translate>
            </span>
          </dt>
          <dd>{jobPostingEntity.description}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="jobpostingApp.jobPosting.location">Location</Translate>
            </span>
          </dt>
          <dd>{jobPostingEntity.location}</dd>
          <dt>
            <span id="postedDate">
              <Translate contentKey="jobpostingApp.jobPosting.postedDate">Posted Date</Translate>
            </span>
          </dt>
          <dd>
            {jobPostingEntity.postedDate ? <TextFormat value={jobPostingEntity.postedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="jobpostingApp.jobPosting.company">Company</Translate>
          </dt>
          <dd>{jobPostingEntity.company ? jobPostingEntity.company.companyId : ''}</dd>
        </dl>
        <Button tag={Link} to="/job-posting" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job-posting/${jobPostingEntity.jobId}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobPostingDetail;
