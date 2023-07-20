import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './resume.reducer';

export const ResumeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const resumeEntity = useAppSelector(state => state.resume.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="resumeDetailsHeading">
          <Translate contentKey="myApp.resume.detail.title">Resume</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="resumeId">
              <Translate contentKey="myApp.resume.resumeId">Resume Id</Translate>
            </span>
          </dt>
          <dd>{resumeEntity.resumeId}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="myApp.resume.title">Title</Translate>
            </span>
          </dt>
          <dd>{resumeEntity.title}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="myApp.resume.content">Content</Translate>
            </span>
          </dt>
          <dd>{resumeEntity.content}</dd>
          <dt>
            <span id="submittedDate">
              <Translate contentKey="myApp.resume.submittedDate">Submitted Date</Translate>
            </span>
          </dt>
          <dd>
            {resumeEntity.submittedDate ? <TextFormat value={resumeEntity.submittedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="myApp.resume.applicant">Applicant</Translate>
          </dt>
          <dd>{resumeEntity.applicant ? resumeEntity.applicant.applicantId : ''}</dd>
        </dl>
        <Button tag={Link} to="/resume" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/resume/${resumeEntity.resumeId}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ResumeDetail;
