import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './application.reducer';

export const ApplicationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const applicationEntity = useAppSelector(state => state.application.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicationDetailsHeading">
          <Translate contentKey="jobpostingApp.application.detail.title">Application</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="applicationId">
              <Translate contentKey="jobpostingApp.application.applicationId">Application Id</Translate>
            </span>
          </dt>
          <dd>{applicationEntity.applicationId}</dd>
          <dt>
            <span id="applicationDate">
              <Translate contentKey="jobpostingApp.application.applicationDate">Application Date</Translate>
            </span>
          </dt>
          <dd>
            {applicationEntity.applicationDate ? (
              <TextFormat value={applicationEntity.applicationDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jobpostingApp.application.jobPosting">Job Posting</Translate>
          </dt>
          <dd>{applicationEntity.jobPosting ? applicationEntity.jobPosting.jobId : ''}</dd>
          <dt>
            <Translate contentKey="jobpostingApp.application.applicant">Applicant</Translate>
          </dt>
          <dd>{applicationEntity.applicant ? applicationEntity.applicant.applicantId : ''}</dd>
        </dl>
        <Button tag={Link} to="/application" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/application/${applicationEntity.applicationId}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApplicationDetail;
