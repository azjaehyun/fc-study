import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJobPosting } from 'app/shared/model/job-posting.model';
import { getEntities as getJobPostings } from 'app/entities/job-posting/job-posting.reducer';
import { IApplicant } from 'app/shared/model/applicant.model';
import { getEntities as getApplicants } from 'app/entities/applicant/applicant.reducer';
import { IApplication } from 'app/shared/model/application.model';
import { getEntity, updateEntity, createEntity, reset } from './application.reducer';

export const ApplicationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const jobPostings = useAppSelector(state => state.jobPosting.entities);
  const applicants = useAppSelector(state => state.applicant.entities);
  const applicationEntity = useAppSelector(state => state.application.entity);
  const loading = useAppSelector(state => state.application.loading);
  const updating = useAppSelector(state => state.application.updating);
  const updateSuccess = useAppSelector(state => state.application.updateSuccess);

  const handleClose = () => {
    navigate('/application' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getJobPostings({}));
    dispatch(getApplicants({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.applicationDate = convertDateTimeToServer(values.applicationDate);

    const entity = {
      ...applicationEntity,
      ...values,
      jobPosting: jobPostings.find(it => it.jobId.toString() === values.jobPosting.toString()),
      applicant: applicants.find(it => it.applicantId.toString() === values.applicant.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          applicationDate: displayDefaultDateTime(),
        }
      : {
          ...applicationEntity,
          applicationDate: convertDateTimeFromServer(applicationEntity.applicationDate),
          jobPosting: applicationEntity?.jobPosting?.jobId,
          applicant: applicationEntity?.applicant?.applicantId,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jobpostingApp.application.home.createOrEditLabel" data-cy="ApplicationCreateUpdateHeading">
            <Translate contentKey="jobpostingApp.application.home.createOrEditLabel">Create or edit a Application</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="applicationId"
                  required
                  readOnly
                  id="application-applicationId"
                  label={translate('jobpostingApp.application.applicationId')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jobpostingApp.application.applicationDate')}
                id="application-applicationDate"
                name="applicationDate"
                data-cy="applicationDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="application-jobPosting"
                name="jobPosting"
                data-cy="jobPosting"
                label={translate('jobpostingApp.application.jobPosting')}
                type="select"
              >
                <option value="" key="0" />
                {jobPostings
                  ? jobPostings.map(otherEntity => (
                      <option value={otherEntity.jobId} key={otherEntity.jobId}>
                        {otherEntity.jobId}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="application-applicant"
                name="applicant"
                data-cy="applicant"
                label={translate('jobpostingApp.application.applicant')}
                type="select"
              >
                <option value="" key="0" />
                {applicants
                  ? applicants.map(otherEntity => (
                      <option value={otherEntity.applicantId} key={otherEntity.applicantId}>
                        {otherEntity.applicantId}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/application" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ApplicationUpdate;
