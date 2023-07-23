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
          <Translate contentKey="applicationApp.application.detail.title">Application</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="applicationId">
              <Translate contentKey="applicationApp.application.applicationId">Application Id</Translate>
            </span>
          </dt>
          <dd>{applicationEntity.applicationId}</dd>
          <dt>
            <span id="applicationDate">
              <Translate contentKey="applicationApp.application.applicationDate">Application Date</Translate>
            </span>
          </dt>
          <dd>
            {applicationEntity.applicationDate ? (
              <TextFormat value={applicationEntity.applicationDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="applicationApp.application.resume">Resume</Translate>
          </dt>
          <dd>{applicationEntity.resume ? applicationEntity.resume.resumeId : ''}</dd>
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
