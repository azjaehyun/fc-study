import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './applicant.reducer';

export const ApplicantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const applicantEntity = useAppSelector(state => state.applicant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicantDetailsHeading">
          <Translate contentKey="jobpostingApp.applicant.detail.title">Applicant</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="applicantId">
              <Translate contentKey="jobpostingApp.applicant.applicantId">Applicant Id</Translate>
            </span>
          </dt>
          <dd>{applicantEntity.applicantId}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jobpostingApp.applicant.name">Name</Translate>
            </span>
          </dt>
          <dd>{applicantEntity.name}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="jobpostingApp.applicant.email">Email</Translate>
            </span>
          </dt>
          <dd>{applicantEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="jobpostingApp.applicant.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{applicantEntity.phone}</dd>
        </dl>
        <Button tag={Link} to="/applicant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/applicant/${applicantEntity.applicantId}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ApplicantDetail;
